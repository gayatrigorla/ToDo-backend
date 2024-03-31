package com.gnc.todo.controller;

import com.gnc.todo.model.ListItem;
import com.gnc.todo.service.ListItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/list-item")
public class ListItemController {

    private ListItemService listItemService;

    @GetMapping("/{id}")
    public List<ListItem> showAllListItems(@PathVariable Long id) {
        log.info("Showing all the list items for id: "+id);
        return listItemService.showAllItems(id);
    }

    @PostMapping("/{id}")
    public void addListItem(@PathVariable("id") Long id, @RequestBody ListItem listItem) {
        log.info("Adding an item to the list");
        listItemService.addListItem(id, listItem);
    }

    @DeleteMapping("/{id}/{item-id}")
    public void deleteListItem(@PathVariable("id") Long id, @PathVariable("item-id") Long itemId) {
        log.info("Deleting an item from the list");
        listItemService.deleteItem(id,itemId);
    }

    @PutMapping("/{id}")
    public void changeStatus(@PathVariable("id") Long id, @RequestBody ListItem listItem) {
    log.info("Changing status of the item");
    listItemService.changeStatus(id, listItem);
    }

    @PutMapping("/{id}/{item-id}")
    public void changeRank(@PathVariable("id") Long id, @PathVariable("item-id") Long itemId, @RequestBody ListItem listItem) {
        log.info("Changing the rank of the list item with id: "+itemId);
        listItemService.changeRank(id, itemId, listItem.getRank());
    }


}
