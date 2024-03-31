package com.gnc.todo.service;

import com.gnc.todo.model.ListItem;
import com.gnc.todo.model.MemoryDataStore;
import com.gnc.todo.model.TodoList;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class ListItemService {

    private MemoryDataStore dataStore;

    public void addlistItem(Long id, String name) {
        Optional<TodoList> listOptional = dataStore.findTodoList(id);
        if(!listOptional.isPresent()) {
            throw new NullPointerException("List woth following id not found");
        }

        TodoList list = listOptional.get();
        ListItem item = new ListItem();
        item.setName(name);
        item.setRank(list.getItems().size()+1);
        item = dataStore.save(item);
        list.getItems().add(item);
        dataStore.save(list);
    }

    public void deleteItem(long id, Long itemId) {

    }
}
