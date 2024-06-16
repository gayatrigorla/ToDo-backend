package com.gnc.todo.repository;

import com.gnc.todo.model.ListItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ListItemRepository extends MongoRepository<ListItem, String> {

    @Query("{'tacoId': ?0}")
    List<ListItem> findByTodoId(String id);

    void deleteByTacoId(String id);
}
