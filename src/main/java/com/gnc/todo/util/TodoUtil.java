package com.gnc.todo.util;

import java.util.Collections;
import java.util.List;

import com.gnc.todo.model.ListItem;

public class TodoUtil {
    private TodoUtil() {}

    public static List<ListItem> sortListItemByRank(List<ListItem> items) {
        Collections.sort(items, (a,b) -> Integer.compare(a.getRank(), b.getRank()));
        return items;
    }
}
