package org.demo.todo.todoapp.repository;

import org.demo.todo.todoapp.model.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<TodoItem, Integer> {
}
