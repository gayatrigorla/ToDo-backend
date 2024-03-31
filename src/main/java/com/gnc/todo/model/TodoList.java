package com.gnc.todo.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class TodoList {
    private Long id;
    private String name;
    private List<ListItem> items = new ArrayList<>(); 
}
