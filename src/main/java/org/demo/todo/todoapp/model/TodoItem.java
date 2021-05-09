package org.demo.todo.todoapp.model;

import org.demo.todo.todoapp.converters.StringListConverter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Entity
public class TodoItem implements Comparable<TodoItem> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "name", unique = true)
    String name;

    @Column(name = "completed")
    Boolean completed;

    @Convert(converter = StringListConverter.class)
    @Column(name = "categories")
    List<String> categories = new ArrayList<>();

    public TodoItem() {
        // Default constructor required for JPA
    }

    public TodoItem(String name, String ... categories) {
        this.name = name;
        this.categories = Arrays.asList(categories);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getCategories() {
        return categories;
    }

    public boolean getCompleted() {
        return completed != null && completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public int compareTo(TodoItem o) {
        return id - o.id;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof TodoItem)
            return ((TodoItem) o).id == id;
        else
            return false;
    }
}
