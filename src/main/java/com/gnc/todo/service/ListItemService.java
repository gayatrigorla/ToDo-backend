package com.gnc.todo.service;

import com.gnc.todo.model.ListItem;
import com.gnc.todo.model.MemoryDataStore;
import com.gnc.todo.model.TodoList;
import org.springframework.stereotype.Service;

@Service
public class ListItemService {

    private MemoryDataStore dataStore;

    private TodoList todoList = new TodoList();

    private ListItem listItem = new ListItem();
    public void addlistItem(Long id, String name) {

    }

    public void deleteItem(long id, Long itemId) {

    }
}
