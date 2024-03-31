package com.gnc.todo.service;

import com.gnc.todo.model.ListItem;
import com.gnc.todo.model.MemoryDataStore;
import com.gnc.todo.model.TodoList;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class ListItemService {

    private MemoryDataStore dataStore;

    public void addListItem(Long id, ListItem listItem) {
        Optional<TodoList> listOptional = dataStore.findTodoList(id);
        if(!listOptional.isPresent()) {
            throw new NullPointerException("List with "+id+" id not found");
        }

        TodoList list = listOptional.get();
        ListItem item = new ListItem();
        item.setName(listItem.getName());
        item.setRank(list.getItems().size()+1);
        item = dataStore.save(item);
        list.getItems().add(item);
        dataStore.save(list);
    }

    public void deleteItem(long id, Long itemId) {
        Optional<TodoList> listOptional = dataStore.findTodoList(id);
        if(!listOptional.isPresent()) {
            throw new NullPointerException("List with "+id+" id not found");
        }

        TodoList list = listOptional.get();
        Optional<ListItem> listItemOptional = dataStore.findListItem(itemId);
        if(!listItemOptional.isPresent()) {
            throw new NullPointerException("List item with "+id+" id not found");
        }

        ListItem item = listItemOptional.get();
        dataStore.delete(item);
        list.getItems().remove(item);
        dataStore.save(list);
    }

    public void changeStatus(long id, ListItem listItem) {
        Optional<TodoList> listOptional = dataStore.findTodoList(id);
        if(!listOptional.isPresent()) {
            throw new NullPointerException("List with "+id+" id not found");
        }

        TodoList list = listOptional.get();
        Optional<ListItem> listItemOptional = dataStore.findListItem(listItem.getId());
        if(!listItemOptional.isPresent()) {
            throw new NullPointerException("List item with "+id+" id not found");
        }

        ListItem item = listItemOptional.get();
        list.getItems().remove(item);
        item.setCompleted(!item.isCompleted());
        dataStore.save(item);
        list.getItems().add(item);
    }

    public List<ListItem> showAllItems(long id) {
        Optional<TodoList> listOptional = dataStore.findTodoList(id);
        if(!listOptional.isPresent()) {
            throw new NullPointerException("List with "+id+" id not found");
        }

        TodoList list = listOptional.get();
        return list.getItems();
    }

    public void changeRank(Long listId, Long itemId, int newRank) {
        Optional<TodoList> listOptional = dataStore.findTodoList(listId);
        if(!listOptional.isPresent()) {
            throw new NullPointerException("List woth following id not found");
        }

        TodoList list = listOptional.get();
        int oldRank = list.getItems().stream().filter(i -> itemId.equals(i.getId())).map(ListItem::getRank).findAny().orElseThrow();
        if(newRank == oldRank) {
            return;
        }

        if(newRank < oldRank) {
            upgradeRank(list.getItems(), oldRank, newRank);
        } else {
            downgradeRank(list.getItems(), oldRank, newRank);
        }

    }

    private void downgradeRank(List<ListItem> items, int oldRank, int newRank) {
        for(int i=oldRank; i<newRank; i++) {
            items.get(i).setRank(i);
            dataStore.save(items.get(i));
        }

        items.get(oldRank-1).setRank(newRank);
        dataStore.save(items.get(oldRank-1));
    }

    private void upgradeRank(List<ListItem> items, int oldRank, int newRank) {
        for(int i=newRank-1; i<oldRank-1; i++) {
            items.get(i).setRank(i+2);
            dataStore.save(items.get(i));
        }

        items.get(oldRank-1).setRank(newRank);
        dataStore.save(items.get(oldRank-1));
    }
}
