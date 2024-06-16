package com.gnc.todo.service;

import com.gnc.todo.model.ListItem;
import com.gnc.todo.model.TodoList;

import java.util.List;
import java.util.Optional;

import com.gnc.todo.repository.ListItemRepository;
import com.gnc.todo.repository.ToDoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class ListItemService {

    @Autowired
    private ListItemRepository listItemRepository;

    @Autowired
    private ToDoListRepository toDoListRepository;

    public void addListItem(String id, ListItem listItem) {
        Optional<TodoList> listOptional = toDoListRepository.findById(id);
        if(!listOptional.isPresent()) {
            throw new NullPointerException("List with "+id+" id not found");
        }

        // TodoList list = listOptional.get();
        ListItem item = new ListItem();
        item.setName(listItem.getName());
        item.setRank(getNextRankForTodoList(id));
        item.setTacoId(id);
        listItemRepository.save(item);
    }

    private int getNextRankForTodoList(String todoId) {
        List<ListItem> items = listItemRepository.findByTodoId(todoId);
        return items.size() + 1;
    }

    public void deleteItem(String itemId) {
        Optional<ListItem> listItemOptional = listItemRepository.findById(itemId);
        if(!listItemOptional.isPresent()) {
            throw new NullPointerException("List item with "+itemId+" id not found");
        }

        ListItem item = listItemOptional.get();
        listItemRepository.delete(item);
    }

    public void changeStatus(String id, ListItem listItem) {
        Optional<ListItem> listItemOptional = listItemRepository.findById(listItem.getId());
        if(!listItemOptional.isPresent()) {
            throw new NullPointerException("List item with "+id+" id not found");
        }

        ListItem item = listItemOptional.get();
        item.setCompleted(listItem.isCompleted());
        listItemRepository.save(item);
    }

    public List<ListItem> showAllItems(String id) {
        List<ListItem> listItems = listItemRepository.findByTodoId(id);
        if(CollectionUtils.isEmpty(listItems)) {
            throw new NullPointerException("List with "+id+" id not found");
        }

        return listItems;
    }

    public void changeRank(String listId, String itemId, int newRank) {
        List<ListItem> listItems = listItemRepository.findByTodoId(listId);
        if(CollectionUtils.isEmpty(listItems)) {
            throw new NullPointerException("List with following id not found");
        }

        int oldRank = listItems.stream().filter(i -> itemId.equals(i.getId())).map(ListItem::getRank).findAny().orElseThrow();
        if(newRank == oldRank) {
            return;
        }

        if(newRank < oldRank) {
            downgradeRank(listItems, oldRank, newRank);
        } else {
            upgradeRank(listItems, oldRank, newRank);
        }

        listItemRepository.saveAll(listItems);

    }

    private void downgradeRank(List<ListItem> items, int oldRank, int newRank) {
        ListItem item = null;

        for(ListItem i: items) {
            if (i.getRank() == oldRank) {
                item = i;
            } else if (i.getRank() >= newRank && i.getRank() < oldRank) {
                i.setRank(i.getRank() + 1);
            }
        }
        item.setRank(newRank);
    }

    private void upgradeRank(List<ListItem> items, int oldRank, int newRank) {
        ListItem item=null;

        for(ListItem i : items) {
            if(i.getRank() == oldRank) {
                item=i;
            } else if(oldRank < i.getRank() && i.getRank() <= newRank) {
                i.setRank(i.getRank()-1);
            }
        }
        item.setRank(newRank);
    }
}
