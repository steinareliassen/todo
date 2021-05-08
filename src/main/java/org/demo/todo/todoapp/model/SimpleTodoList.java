package org.demo.todo.todoapp.model;

public class SimpleTodoList {

    private int id;
    private String name;

    public SimpleTodoList(TodoList todoList) {
        id = todoList.getId();
        name = todoList.getName();
    }

}
