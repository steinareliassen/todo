package org.demo.todo.todoapp.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.demo.todo.todoapp.model.SimpleTodoList;
import org.demo.todo.todoapp.model.TodoItem;
import org.demo.todo.todoapp.model.TodoList;
import org.demo.todo.todoapp.repository.ListRepository;
import org.demo.todo.todoapp.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.PathParam;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TodoController {

    @Autowired
    ListRepository listRepository;

    @Autowired
    TodoRepository todoRepository;

    @ApiOperation(value = "Create a list with a given name", tags = {"list"})
    @PostMapping(value = "/list", produces = "application/json")
    int createList(@RequestBody String listName) {
        return listRepository.save(new TodoList(listName)).getId();
    }

    @ApiOperation(value = "Delete a list with a given listId, and all connected todo items.", tags = {"list"})
    @DeleteMapping(value= "/list/{listId}")
    void deleteList(@PathVariable("listId") String listId) {
        if (listRepository.getOne(Integer.parseInt(listId)).getTodoItems().size() != 0)
            System.out.println("Delete content");
        listRepository.deleteById(Integer.parseInt(listId));
    }

    @ApiOperation(value = "Get all lists in a simple format, containing only id and name", tags = {"list"})
    @GetMapping(value = "/lists", produces = "application/json")
    List<SimpleTodoList> getLists() {
        return listRepository.findAll().stream().map(SimpleTodoList::new)
                .collect(Collectors.toUnmodifiableList());
    }

    @ApiOperation(value = "Get a list with a given listId", tags = {"list"})
    @GetMapping(value = "/list/{listId}", produces = "application/json")
    ResponseEntity<TodoList> getList(@PathVariable("listId") int listId, String category) {
        TodoList todoList = listRepository.findById(listId)
                .orElseThrow(()->new IllegalStateException("No list with that id exists"));
        return ResponseEntity.ok().body(
                category != null ? new TodoList(
                        todoList.getId(), todoList.getName(),
                        todoList.getTodoItems().stream()
                                .filter(todoItem ->
                                        todoItem.getCategories().contains(category)).collect(Collectors.toList())
                ) : todoList
        );
    }

    @ApiOperation(value = "Add a todo item to the list with given Id.", tags = {"todo"})
    @PostMapping(value = "/list/{listId}", produces = "application/json")
    TodoItem createTodoEntry(@PathVariable("listId") int listId, @RequestBody TodoItem todoItem) {
        TodoList todoList = listRepository.findById(listId)
                .orElseThrow(()->new IllegalStateException("No list with that id exists"));
        todoItem = todoRepository.save(todoItem); // Fetch with correct Id
        todoList.addTodoItem(todoItem);
        listRepository.save(todoList);
        return todoItem;
    }

    @ApiOperation(value = "Update a todo item with a given id to the list with given Id.", tags = {"todo"})
    @PutMapping(value = "/list/{listId}/todoItem/{itemId}", produces = "application/json")
    TodoItem updateTodoEntry(@PathVariable("listId") int listId,
                             @PathVariable("itemId") int itemId,
                             @RequestBody TodoItem todoItem) {

        TodoList todoList = listRepository.findById(listId)
                .orElseThrow(()->new IllegalStateException("No list with that id exists"));
        TodoItem currentItem = todoRepository.findById(itemId)
                .orElseThrow(()->new IllegalStateException("No todo item with that id exists, use POST operation to create new"));

        if (!todoList.getTodoItems().contains(currentItem)) throw new IllegalStateException("Todo item is not part of this list");

        todoItem.setId(currentItem.getId());
        todoItem = todoRepository.save(todoItem);
        return todoItem;
    }

}
