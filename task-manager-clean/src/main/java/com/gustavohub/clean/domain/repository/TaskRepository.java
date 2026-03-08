package com.gustavohub.clean.domain.repository;

import com.gustavohub.clean.domain.entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {

    Task save(Task task);
    List<Task> findAll();
    Optional<Task> findById(String id);
    void deleteById(String id);
}