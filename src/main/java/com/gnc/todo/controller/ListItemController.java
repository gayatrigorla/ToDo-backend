package com.gnc.todo.controller;

import com.gnc.todo.model.ListItem;
import com.gnc.todo.service.ListItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/list-item")
public class ListItemController {

    private ListItemService listItemService;

    @PostMapping("/{id}/{item-name}")
    public void addListItem(@PathVariable("id") Long id, @PathVariable("item-name") String itemName) {
        log.info("Adding an item to the list");
        listItemService.addlistItem(id, itemName);
    }

    @DeleteMapping("/delete/{id}/{item-id}")
    public void deleteListItem(@PathVariable("id") Long id, @PathVariable("item-id") Long itemId) {
        log.info("Deleting an item from the list");
        listItemService.deleteItem(id,itemId);
    }

    @PutMapping("/change-status/{id}/{item-id}")
        public void changeStatus(@PathVariable("id") Long id, @PathVariable("item-id") Long itemId, @RequestBody ListItem listItem) {
    }

}
