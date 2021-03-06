package org.demo.todo.todoapp.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TodoList {

    public TodoList() {
        // Default constructor required for JPA
    }
    public TodoList(int id, String name, List<TodoItem> todoItems) {
        this.id = id;
        this.name = name;
        this.todoItems = todoItems;
    }

    public TodoList(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private int id;

    @Column(name="name", unique = true)
    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<TodoItem> todoItems = new ArrayList<>();

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<TodoItem> getTodoItems() {
        return todoItems;
    }

    public void removeTodoItems() {
        todoItems = new ArrayList<>();
    }

    public void removeTodoItem(TodoItem todoItem) {
        todoItems.remove(todoItem);
    }

    public void addTodoItem(TodoItem todoItem) {
        todoItems.add(todoItem);
    }

}
