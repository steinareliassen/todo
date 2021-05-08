package org.demo.todo.todoapp.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.demo.todo.todoapp.model.TodoItem;
import org.demo.todo.todoapp.model.TodoList;
import org.demo.todo.todoapp.repository.ListRepository;
import org.demo.todo.todoapp.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TodoController {

    @Autowired
    TodoRepository todoRepository;

    @Autowired
    ListRepository listRepository;

    @ApiOperation("Get all Tutorials")
    @ApiResponse(code = 200, message = "ok", response = Integer.class)
    @GetMapping(value = "/todo", produces = "application/json")
    List<TodoItem> getTodoItem() {
        return todoRepository.getTodoItems("myList");
    }

    @PostMapping(value = "/list", produces = "application/json")
    String createList(String listName) {
        return listRepository.save(new TodoList(listName)).getId()+"";
    }

    @DeleteMapping(value= "/list/{listId}")
    void deleteList(@PathVariable("listId") String listId) {

    }

    @GetMapping(value = "/lists", produces = "application/json")
    List<TodoList> getLists() {
        return listRepository.findAll();
    }

}
