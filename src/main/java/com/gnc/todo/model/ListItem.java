package com.gnc.todo.model;

import lombok.Data;

@Data
public class ListItem {
    
    private Long id;
    private String name;
    private int rank;
    private boolean completed = false;
}
