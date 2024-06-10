package com.gnc.todo.repository;

import com.gnc.todo.model.TodoList;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ToDoListRepository extends MongoRepository<TodoList,String> {
}
