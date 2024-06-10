package com.gnc.todo.service;

import com.gnc.todo.model.TodoList;

import java.util.List;
import java.util.Optional;

import com.gnc.todo.repository.ToDoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListService {

    @Autowired
    private ToDoListRepository toDoListRepository;

    public void addList(TodoList todoList) {
        toDoListRepository.save(todoList);
    }

    public void renameList(String id, String name) {
        Optional<TodoList> listOptional = toDoListRepository.findById(id);
        if(listOptional.isEmpty()) {
            throw new NullPointerException("List with "+id+" id not found");
        }

        TodoList list = listOptional.get();
        list.setName(name);
        toDoListRepository.save(list);
    }

    public void deleteList(String id) {
        Optional<TodoList> listOptional = toDoListRepository.findById(id);
        if(listOptional.isEmpty()) {
            throw new NullPointerException("List with "+id+" id not found");
        }

        TodoList list = listOptional.get();
        toDoListRepository.delete(list);
    }

    public List<TodoList> showLists() {
        return toDoListRepository.findAll();
    }

    public TodoList getList(String id) {
        Optional<TodoList> listOptional = toDoListRepository.findById(id);
        if(listOptional.isEmpty()) {
            throw new NullPointerException("List with "+id+" id not found");
        }

        return listOptional.get();
    }
}
