package com.gnc.todo.service;

import com.gnc.todo.model.MemoryDataStore;
import com.gnc.todo.model.TodoList;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListService {

    @Autowired
    private MemoryDataStore dataStore;

    public void addList(TodoList todoList) {
        dataStore.save(todoList);
    }

    public void renameList(Long id, String name) {
        Optional<TodoList> listOptional = dataStore.findTodoList(id);
        if(!listOptional.isPresent()) {
            throw new NullPointerException("List with "+id+" id not found");
        }

        TodoList list = listOptional.get();
        list.setName(name);
        dataStore.save(list);
    }

    public void deleteList(Long id) {
        Optional<TodoList> listOptional = dataStore.findTodoList(id);
        if(!listOptional.isPresent()) {
            throw new NullPointerException("List with "+id+" id not found");
        }

        TodoList list = listOptional.get();
        dataStore.delete(list);
    }

    public List<TodoList> showLists() {
        return dataStore.getAllTodoLists();
    }

    public TodoList getList(long id) {
        Optional<TodoList> listOptional = dataStore.findTodoList(id);
        if(!listOptional.isPresent()) {
            throw new NullPointerException("List with "+id+" id not found");
        }

        TodoList list = listOptional.get();
        return list;
    }
}
