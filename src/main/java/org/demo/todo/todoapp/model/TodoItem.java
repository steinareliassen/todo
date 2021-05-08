package org.demo.todo.todoapp.model;

import org.demo.todo.todoapp.converters.StringListConverter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TodoItem implements Comparable<TodoItem>{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private int id;

    @Column(name="name")
    String name;

    @Column(name = "completed")
    Boolean completed;

    @Convert(converter = StringListConverter.class)
    @Column(name = "categories")
    List<String> categories = new ArrayList<>();

    public TodoItem() {
        // Default constructor required for JPA
    }
    public TodoItem(String name) {
        this.name = name;
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

    @Override
    public int compareTo(TodoItem o) {
        return id-o.id;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof TodoItem)
            return ((TodoItem) o).id == id;
        else
            return false;
    }
}
