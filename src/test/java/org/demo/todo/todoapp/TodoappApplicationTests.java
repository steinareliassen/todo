package org.demo.todo.todoapp;

import org.demo.todo.todoapp.dal.TodoDAL;
import org.demo.todo.todoapp.model.SimpleTodoList;
import org.demo.todo.todoapp.model.TodoItem;
import org.demo.todo.todoapp.model.TodoList;
import org.demo.todo.todoapp.repository.ListRepository;
import org.demo.todo.todoapp.repository.TodoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
class TodoappApplicationTests {

    @Autowired
    private TodoDAL todoDAL;

    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private ListRepository listRepository;

    @BeforeEach
    public void deleteEntries() {
        listRepository.deleteAll();
        todoRepository.deleteAll();
    }

    @Test
    public void getLists() {
        todoDAL.createList("my_list1");
        todoDAL.createList("my_list2");
        todoDAL.createList("my_list3");

        List<SimpleTodoList> simpleTodoLists = todoDAL.getLists();
        assert simpleTodoLists.size() == 3;
        assert listRepository.findAll().stream()
                .filter(element -> element.getName().equals("my_list1")).count() == 1;
        assert listRepository.findAll().stream()
                .filter(element -> element.getName().equals("my_list2")).count() == 1;
        assert listRepository.findAll().stream()
                .filter(element -> element.getName().equals("my_list3")).count() == 1;

    }

    @Test
    public void createList() {
        todoDAL.createList("my_list");
        assert listRepository.findAll().stream()
                .filter(element -> element.getName().equals("my_list")).count() == 1;
    }

    @Test
    public void createDuplicateList() {
        Assertions.assertThrows(IllegalStateException.class,
                () -> {
                    todoDAL.createList("my_list");
                    todoDAL.createList("my_list");
                }
        );
    }

    @Test
    public void getList() {
        int listId = todoDAL.createList("my_list");
        TodoList list = todoDAL.getList(listId, null);
        assert list.getName().equals("my_list");
    }

    @Test
    public void getListFiltered() {
        int listId = todoDAL.createList("my_list");
        todoDAL.createTodoItem(listId, new TodoItem("my_item_1","cat_a", "cat_b"));
        todoDAL.createTodoItem(listId, new TodoItem("my_item_2","cat_a", "cat_c"));

        assert todoDAL.getList(listId, "cat_a").getTodoItems().size() == 2;
        assert todoDAL.getList(listId, "cat_b").getTodoItems().size() == 1;
        assert todoDAL.getList(listId, "cat_c").getTodoItems().size() == 1;

    }

    @Test
    public void deleteList() {
        int listId = todoDAL.createList("my_list");
        TodoItem item = todoDAL.createTodoItem(listId, new TodoItem("my_item"));
        assert todoRepository.findById(item.getId()).orElse(null) != null;
        assert listRepository.findById(listId).orElse(null) != null;
        todoDAL.deleteList(listId);
        assert listRepository.findById(listId).orElse(null) == null;
        assert todoRepository.findById(item.getId()).orElse(null) == null;
    }

    @Test
    public void createTodoItem() {
        int listId = todoDAL.createList("my_list");
        TodoItem item = todoDAL.createTodoItem(listId, new TodoItem("my_item"));
        assert todoRepository.findById(item.getId()).orElse(null) != null;
        TodoList list = listRepository.findById(listId).orElseThrow(() -> new RuntimeException("Should not happen"));
        assert list.getTodoItems().contains(item);
    }

	@Test
	public void createDuplicateTodoItem() {
		Assertions.assertThrows(IllegalStateException.class,
				() -> {
					int listId = todoDAL.createList("my_list");
					todoDAL.createTodoItem(listId, new TodoItem("my_item"));
					todoDAL.createTodoItem(listId, new TodoItem("my_item"));
				}
		);
	}

    @Test
    public void updateTodoItem() {
        int listId = todoDAL.createList("my_list");
        TodoItem item = todoDAL.createTodoItem(listId, new TodoItem("my_item"));
        todoDAL.updateTodoItem(listId, item.getId(),new TodoItem("my_new_name","my_cat"));

        assert todoDAL.getList(listId,"my_cat").getTodoItems().get(0).getName().equals("my_new_name");
    }

}

