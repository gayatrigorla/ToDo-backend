package com.gnc.todo.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "TODO_LIST")
public class TodoList {
    @Id
    private String id;
    private String name;
}
