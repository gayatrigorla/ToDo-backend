package com.gnc.todo.service;

import com.gnc.todo.model.ListItem;
import com.gnc.todo.model.TodoList;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.gnc.todo.repository.ListItemRepository;
import com.gnc.todo.repository.ToDoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        TodoList list = listOptional.get();
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

    public void deleteItem(String id, String itemId) {
        Optional<TodoList> listOptional = toDoListRepository.findById(id);
        if(!listOptional.isPresent()) {
            throw new NullPointerException("List with "+id+" id not found");
        }

        TodoList list = listOptional.get();
        Optional<ListItem> listItemOptional = listItemRepository.findById(itemId);
        if(!listItemOptional.isPresent()) {
            throw new NullPointerException("List item with "+id+" id not found");
        }

        ListItem item = listItemOptional.get();
        listItemRepository.delete(item);
    }

    public void changeStatus(String id, ListItem listItem) {
        Optional<TodoList> listOptional = toDoListRepository.findById(id);
        if(!listOptional.isPresent()) {
            throw new NullPointerException("List with "+id+" id not found");
        }

        TodoList list = listOptional.get();
        Optional<ListItem> listItemOptional = listItemRepository.findById(listItem.getId());
        if(!listItemOptional.isPresent()) {
            throw new NullPointerException("List item with "+id+" id not found");
        }

        ListItem item = listItemOptional.get();
        item.setCompleted(!item.isCompleted());
        listItemRepository.save(item);
    }

    public List<ListItem> showAllItems(String id) {
        List<ListItem> listItems = listItemRepository.findByTodoId(id);
        if(Objects.isNull(listItems)) {
            throw new NullPointerException("List with "+id+" id not found");
        }

        return listItems;
    }

    public void changeRank(String listId, String itemId, int newRank) {
        List<ListItem> listItems = listItemRepository.findByTodoId(listId);
        if(Objects.isNull(listItems)) {
            throw new NullPointerException("List with following id not found");
        }

        int oldRank = listItems.stream().filter(i -> itemId.equals(i.getId())).map(ListItem::getRank).findAny().orElseThrow();
        if(newRank == oldRank) {
            return;
        }

        if(newRank < oldRank) {
            upgradeRank(listItems, oldRank, newRank);
        } else {
            downgradeRank(listItems, oldRank, newRank);
        }

    }

    private void downgradeRank(List<ListItem> items, int oldRank, int newRank) {
        for(int i=oldRank; i<newRank; i++) {
            items.get(i).setRank(i);
            listItemRepository.save(items.get(i));
        }

        items.get(oldRank-1).setRank(newRank);
        listItemRepository.save(items.get(oldRank-1));
    }

    private void upgradeRank(List<ListItem> items, int oldRank, int newRank) {
        for(int i=newRank-1; i<oldRank-1; i++) {
            items.get(i).setRank(i+2);
            listItemRepository.save(items.get(i));
        }

        items.get(oldRank-1).setRank(newRank);
        listItemRepository.save(items.get(oldRank-1));
    }
}
