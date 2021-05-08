package org.demo.todo.todoapp.model;

import javax.persistence.*;

@Entity
public class TodoList {

    public TodoList() {
        // Default constructor required for JPA
    }

    public TodoList(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private int id;

    @Column(name="name")
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
