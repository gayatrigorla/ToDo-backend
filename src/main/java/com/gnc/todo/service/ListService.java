package com.gnc.todo.service;

import com.gnc.todo.model.MemoryDataStore;
import com.gnc.todo.model.TodoList;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListService {

    private MemoryDataStore dataStore;

    public void addList(String name) {
        TodoList todoList = new TodoList();
        todoList.setName(name);
        dataStore.save(todoList);
    }

    public void renameList(Long id, String name) {
        Optional<TodoList> listOptional = dataStore.findTodoList(id);
        if(!listOptional.isPresent()) {
            throw new NullPointerException("List woth following id not found");
        }

        TodoList list = listOptional.get();
        list.setName(name);
        dataStore.save(list);
    }

    public void deleteList(Long id) {
        Optional<TodoList> listOptional = dataStore.findTodoList(id);
        if(!listOptional.isPresent()) {
            throw new NullPointerException("List woth following id not found");
        }

        TodoList list = listOptional.get();
        dataStore.delete(list);
    }
}
