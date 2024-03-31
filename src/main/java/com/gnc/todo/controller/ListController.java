package com.gnc.todo.controller;

import com.gnc.todo.model.TodoList;
import com.gnc.todo.service.ListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/list")
public class ListController {

    public ListService listService;

    @PostMapping("/{name}")
    public void addList(@PathVariable String name) {
        log.info("Adding list: "+ name);
        listService.addList(name);
    }

    @PutMapping("/{id}/{new-name}")
    public void renameList(@PathVariable("id") Long id, @PathVariable("new-name") String updatedName) {
        log.info("Changing the list name to "+updatedName);
        listService.renameList(id,updatedName);
    }

    @DeleteMapping("/{id}")
    public void deleteList(@PathVariable Long id) {
        log.info("Deleting the list");
        listService.deleteList(id);
    }

}
