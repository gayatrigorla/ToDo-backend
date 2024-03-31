package com.gnc.todo.model;

import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MemoryDataStore {
    
    private Map<Long, TodoList> listMap = new TreeMap<>();
    private Map<Long, ListItem> listItemMap = new TreeMap<>();

    private long newListId = 1l;
    private long newListItemId = 1l;

    public void save(TodoList todoList) {
        if(todoList.getId() == null) {
            todoList.setId(newListId++);
        }
        listMap.put(todoList.getId(), todoList);
    }

    public void save(ListItem listItem) {
        if(listItem.getId() != null) {
            throw new IllegalStateException("id should be populated dynamically");
        }
        listItem.setId(newListItemId);
        listItemMap.put(newListItemId, listItem);
        newListItemId++;
    }

    public void delete(TodoList todoList) {
        if(!listMap.containsKey(todoList.getId())) {
            log.warn("list not found, hence can't be deleted");
        }

        listMap.get(todoList.getId()).getItems().forEach(i -> delete(i));
        listMap.remove(todoList.getId());
    }

    public void delete(ListItem listItem) {
        if(!listItemMap.containsKey(listItem.getId())) {
            log.warn("list item not found, hence can't be deleted");
        }

        listItemMap.remove(listItem.getId());
    }

}
