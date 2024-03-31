package com.gnc.todo.service;

import com.gnc.todo.model.MemoryDataStore;
import com.gnc.todo.model.TodoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListService {

    private MemoryDataStore dataStore;

    private TodoList todoList = new TodoList();
    public void addList(String name) {
        todoList.setName(name);
        dataStore.save(todoList);
    }

    public void renameList(Long id, String name) {
        todoList.setName(name);
        todoList.setId(id);
        dataStore.save(todoList);
    }

    public void deleteList(Long id) {
        todoList.setId(id);
        dataStore.delete(todoList);
    }
}
