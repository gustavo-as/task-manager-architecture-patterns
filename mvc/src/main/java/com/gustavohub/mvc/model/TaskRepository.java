package com.gustavohub.mvc.model;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {

    Task save(Task task);
    List<Task> findAll();
    Optional<Task> findById(String id);
    Task update(Task task);
    void deleteById(String id);
}
