package org.demo.todo.todoapp.repository;

import org.demo.todo.todoapp.model.TodoItem;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TodoRepository {

    public List<TodoItem> getTodoItems(String list) {
        ArrayList<TodoItem> myList = new ArrayList<>();
        myList.add(new TodoItem("name", "category"));
        return myList;
    }

    public String createList(String listName) {
        return "123";
    }

}
