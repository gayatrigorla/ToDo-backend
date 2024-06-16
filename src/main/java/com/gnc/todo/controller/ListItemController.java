package com.gnc.todo.controller;

import com.gnc.todo.model.ListItem;
import com.gnc.todo.service.ListItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/list-item")
public class ListItemController {

    @Autowired
    private ListItemService listItemService;

    @GetMapping("/{id}")
    public List<ListItem> showAllListItems(@PathVariable String id) {
        log.info("Showing all the list items for id: "+id);
        return listItemService.showAllItems(id);
    }

    @PostMapping("/{id}")
    public void addListItem(@PathVariable("id") String id, @RequestBody ListItem listItem) {
        log.info("Adding an item to the list");
        listItemService.addListItem(id, listItem);
    }

    @DeleteMapping("/{item-id}")
    public void deleteListItem(@PathVariable("item-id") String itemId) {
        log.info("Deleting an item from the list");
        listItemService.deleteItem(itemId);
    }

    @PatchMapping("/{item-id}")
    public void changeStatus(@PathVariable("item-id") String id, @RequestBody ListItem listItem) {
    log.info("Changing status of the item");
    listItemService.changeStatus(id, listItem);
    }

    @PutMapping("/{id}/{item-id}")
    public void changeRank(@PathVariable("id") String id, @PathVariable("item-id") String itemId, @RequestBody ListItem listItem) {
        log.info("Changing the rank of the list item with id: "+itemId);
        listItemService.changeRank(id, itemId, listItem.getRank());
    }


}
