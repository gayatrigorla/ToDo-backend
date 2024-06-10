package com.gnc.todo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "SUBLISTS")
public class ListItem {
    @Id
    private String id;
    private String tacoId;
    private String name;
    private int rank;
    private boolean completed = false;
}
