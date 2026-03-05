package com.gustavohub.clean.infrastructure.repository;

import com.gustavohub.clean.domain.entity.Task;
import com.gustavohub.clean.domain.repository.TaskRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class InMemoryTaskRepository implements TaskRepository {

    private final Map<String, Task> storage = new HashMap<>();

    @Override
    public Task save(Task task) {
        storage.put(task.getId(), task);
        return task;
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Optional<Task> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public Task update(Task task) {
        storage.put(task.getId(), task);
        return task;
    }

    @Override
    public void deleteById(String id) {
        storage.remove(id);
    }
}
