package com.gnc.todo.controller;

import com.gnc.todo.model.TodoList;
import com.gnc.todo.service.ListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/list")
public class ListController {


    @Autowired
    public ListService listService;

    @GetMapping()
    public List<TodoList> showAllLists() {
        log.info("Showing all the lists");
        return listService.showLists();
    }

    @GetMapping("/{id}")
    public TodoList getTodoList(@PathVariable long id) {
        log.info("Getting todo list based on id");
        return listService.getList(id);
    }


    @PostMapping()
    public void addList(@RequestBody TodoList todoList) {
        log.info("Adding list: "+ todoList.getName());
        listService.addList(todoList);
    }

    @PutMapping("/{id}")
    public void renameList(@PathVariable("id") Long id,@RequestBody TodoList todoList) {
        log.info("Changing the list name to "+todoList.getName());
        listService.renameList(id,todoList.getName());
    }

    @DeleteMapping("/{id}")
    public void deleteList(@PathVariable Long id) {
        log.info("Deleting the list");
        listService.deleteList(id);
    }

}
