package org.demo.todo.todoapp.controllers;

import io.swagger.annotations.ApiOperation;
import org.demo.todo.todoapp.dal.TodoDAL;
import org.demo.todo.todoapp.model.SimpleTodoList;
import org.demo.todo.todoapp.model.TodoItem;
import org.demo.todo.todoapp.model.TodoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TodoController {

    @Autowired
    TodoDAL todoDAL;

    @ApiOperation(value = "Create a list with a given name", tags = {"list"})
    @PostMapping(value = "/list", produces = "application/json")
    int createList(@RequestBody String listName) {
        return todoDAL.createList(listName);
    }

    @ApiOperation(value = "Delete a list with a given listId, and all connected todo items.", tags = {"list"})
    @DeleteMapping(value= "/list/{listId}")
    void deleteList(@PathVariable("listId") int listId) {
        todoDAL.deleteList(listId);
    }

    @ApiOperation(value = "Get all lists in a simple format, containing only id and name", tags = {"list"})
    @GetMapping(value = "/lists", produces = "application/json")
    List<SimpleTodoList> getLists() {
        return todoDAL.getLists();
    }

    @ApiOperation(value = "Get a list with a given listId", tags = {"list"})
    @GetMapping(value = "/list/{listId}", produces = "application/json")
    TodoList getList(@PathVariable("listId") int listId, String category) {
        return todoDAL.getList(listId, category);
    }

    @ApiOperation(value = "Add a todo item to the list with given Id.", tags = {"todo"})
    @PostMapping(value = "/list/{listId}", produces = "application/json")
    TodoItem createTodoItem(@PathVariable("listId") int listId, @RequestBody TodoItem todoItem) {
        return todoDAL.createTodoItem(listId, todoItem);
    }

    @ApiOperation(value = "Update a todo item with a given id to the list with given Id.", tags = {"todo"})
    @PutMapping(value = "/list/{listId}/todoItem/{itemId}", produces = "application/json")
    TodoItem updateTodoItem(@PathVariable("listId") int listId,
                            @PathVariable("itemId") int itemId,
                            @RequestBody TodoItem todoItem) {
        return todoDAL.updateTodoItem(listId, itemId, todoItem);
    }

    @ApiOperation(value = "Delete a todo item with a given id to the list with given Id.", tags = {"todo"})
    @DeleteMapping(value = "/list/{listId}/todoItem/{itemId}", produces = "application/json")
    void deleteTodoItem(@PathVariable("listId") int listId,
                        @PathVariable("itemId") int itemId) {
        todoDAL.deleteTodoItem(listId, itemId);
    }

    @ApiOperation(value = "Flag a todo item with a given id to the list with given Id with completed status.", tags = {"todo"})
    @PutMapping(value = "/list/{listId}/todoItem/{itemId}/flag/{completed}", produces = "application/json")
    void flagTodoItem(@PathVariable("listId") int listId,
                            @PathVariable("itemId") int itemId,
                            @PathVariable("completed") boolean completed) {
        todoDAL.flagTodoItem(listId, itemId, completed );
    }

}
