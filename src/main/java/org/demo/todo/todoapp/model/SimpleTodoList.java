package org.demo.todo.todoapp.model;

public class SimpleTodoList {

    public SimpleTodoList(TodoList todoList) {
        id = todoList.getId();
        name = todoList.getName();
    }

    private int id;
    private String name;

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }


}
