package org.demo.todo.todoapp.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.demo.todo.todoapp.model.TodoItem;
import org.demo.todo.todoapp.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TodoController {

    @Autowired
    TodoRepository todoRepository;

    @ApiOperation("Get all Tutorials")
    @ApiResponse(code = 200, message = "ok", response = Integer.class)
    @GetMapping(value = "/todo", produces = "application/json")
    List<TodoItem> getTodoItem() {
        return todoRepository.getTodoItems("myList");
    }
}
