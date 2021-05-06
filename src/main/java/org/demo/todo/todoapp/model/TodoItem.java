package org.demo.todo.todoapp.model;

import java.util.ArrayList;
import java.util.List;

public class TodoItem {

    String itemName;
    List<String> categories;

    public TodoItem(String itemName, String category) {
        this.itemName = itemName;
        this.categories = new ArrayList<>();
        categories.add(category);
    }

    public String getItemName() {
        return itemName;
    }

    public List<String> getCategories() {
        return categories;
    }
}
