package org.demo.todo.todoapp.dal;

import org.demo.todo.todoapp.model.SimpleTodoList;
import org.demo.todo.todoapp.model.TodoItem;
import org.demo.todo.todoapp.model.TodoList;
import org.demo.todo.todoapp.repository.ListRepository;
import org.demo.todo.todoapp.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TodoDAL {
    @Autowired
    ListRepository listRepository;

    @Autowired
    TodoRepository todoRepository;

    public int createList(String listName) {
        try {
            return listRepository.save(new TodoList(listName)).getId();
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Attempting to store a list with a name that already exists");
        }
    }

    public void deleteList(int listId) {
        TodoList todoList = listRepository.findById(listId)
                .orElseThrow(()->new IllegalStateException("No list with that id exists"));
        List<TodoItem> todoItems = todoList.getTodoItems();
        todoList.removeTodoItems();
        listRepository.save(todoList);

        todoItems.forEach(todoItem -> todoRepository.deleteById(todoItem.getId()));
        listRepository.deleteById(listId);
    }

    public List<SimpleTodoList> getLists() {
        return listRepository.findAll().stream().map(SimpleTodoList::new)
                .collect(Collectors.toUnmodifiableList());
    }

    public TodoList getList(int listId, String category) {
        TodoList todoList = listRepository.findById(listId)
                .orElseThrow(()->new IllegalStateException("No list with that id exists"));
        return
            category != null ? new TodoList(
                    todoList.getId(), todoList.getName(),
                    todoList.getTodoItems().stream()
                            .filter(todoItem ->
                                    todoItem.getCategories().contains(category)).collect(Collectors.toList())
            ) : todoList;

    }

    public TodoItem createTodoItem(int listId, TodoItem todoItem) {
        TodoList todoList = listRepository.findById(listId)
                .orElseThrow(()->new IllegalStateException("No list with that id exists"));

        try {
            todoItem = todoRepository.save(todoItem); // Fetch with correct Id
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Attempting to store a todo item with a name that already exists");
        }

        todoList.addTodoItem(todoItem);
        listRepository.save(todoList);
        return todoItem;
    }

    public TodoItem updateTodoItem(int listId, int itemId, TodoItem todoItem) {
        TodoList todoList = listRepository.findById(listId)
                .orElseThrow(()->new IllegalStateException("No list with that id exists"));
        TodoItem currentItem = todoRepository.findById(itemId)
                .orElseThrow(()->new IllegalStateException("No todo item with that id exists, use POST operation to create new"));
        if (!todoList.getTodoItems().contains(currentItem)) throw new IllegalStateException("Todo item is not part of this list");

        todoItem.setId(currentItem.getId());
        todoItem = todoRepository.saveAndFlush(todoItem);
        return todoItem;
    }

    public void deleteTodoItem(int listId, int itemId) {
        TodoList todoList = listRepository.findById(listId)
                .orElseThrow(()->new IllegalStateException("No list with that id exists, no todo item deleted"));
        TodoItem todoItem = todoRepository.findById(itemId)
                .orElseThrow(()->new IllegalStateException("No todo item with that id exists, no todo item deleted"));
        if (!todoList.getTodoItems().contains(todoItem))
            throw new IllegalStateException("Todo item is not part of this list, no todo item deleted");
        todoList.removeTodoItem(todoItem);
    }

    public void flagTodoItem(int listId, int itemId, boolean completed) {
        TodoList todoList = listRepository.findById(listId)
                .orElseThrow(()->new IllegalStateException("No list with that id exists, no todo item deleted"));
        TodoItem todoItem = todoRepository.findById(itemId)
                .orElseThrow(()->new IllegalStateException("No todo item with that id exists, completed status not updated"));
        if (!todoList.getTodoItems().contains(todoItem))
            throw new IllegalStateException("Todo item is not part of this list, completed status not updated");
        todoItem.setCompleted(completed);
        todoRepository.save(todoItem);
    }

}
