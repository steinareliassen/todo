package org.demo.todo.todoapp.repository;

import org.demo.todo.todoapp.model.TodoItem;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TodoRepository {

    public List<TodoItem> getTodoItems(String list) {
        ArrayList<TodoItem> myList = new ArrayList<>();
        myList.add(new TodoItem("name", "category"));
        return myList;
    }

}
