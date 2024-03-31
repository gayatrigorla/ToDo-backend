package com.gnc.todo.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

import com.gnc.todo.util.TodoUtil;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MemoryDataStore {
    
    private Map<Long, TodoList> listMap = new TreeMap<>();
    private Map<Long, ListItem> listItemMap = new TreeMap<>();

    private long newListId = 1l;
    private long newListItemId = 1l;

    public TodoList save(TodoList todoList) {
        if(todoList.getId() == null) {
            todoList.setId(newListId++);
        }
        todoList.getItems().forEach(i -> save(i));
        listMap.put(todoList.getId(), todoList);
        return todoList;
    }

    public ListItem save(ListItem listItem) {
        if(listItem.getId() == null) {
            listItem.setId(newListItemId++);
        }
        listItemMap.put(listItem.getId(), listItem);
        return listItem;
    }

    public void delete(TodoList todoList) {
        if(!listMap.containsKey(todoList.getId())) {
            log.warn("list not found, hence can't be deleted");
        }

        listMap.get(todoList.getId()).getItems().forEach(i -> delete(i));
        listMap.remove(todoList.getId());
    }

    public void delete(ListItem listItem) {
        if(!listItemMap.containsKey(listItem.getId())) {
            log.warn("list item not found, hence can't be deleted");
        }

        listItemMap.remove(listItem.getId());
    }

    public Optional<TodoList> findTodoList(Long id) {
        if(listMap.containsKey(id)) {
            TodoList list = listMap.get(id);
            Set<Long> ids = new HashSet<>();
            list.getItems().forEach(i -> ids.add(i.getId()));

            list.getItems().clear();
            for(Long idd: ids) {
                list.getItems().add(findListItem(idd).get());
            }
            TodoUtil.sortListItemByRank(list.getItems());
            return Optional.of(list);
        }

        return Optional.empty();
    }

    public Optional<ListItem> findListItem(Long id) {
        if(listItemMap.containsKey(id)) {
            return Optional.of(listItemMap.get(id));
        }

        return Optional.empty();
    }

    public List<TodoList> getAllTodoLists() {
        return new ArrayList<>(listMap.values());
    }

}
