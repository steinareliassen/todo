package org.demo.todo.todoapp.repository;

import org.demo.todo.todoapp.model.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListRepository extends JpaRepository<TodoList, Integer> {
}
