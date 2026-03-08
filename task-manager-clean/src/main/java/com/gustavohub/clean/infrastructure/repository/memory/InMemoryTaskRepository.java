package com.gustavohub.clean.infrastructure.repository.memory;

import com.gustavohub.clean.domain.entity.Task;
import com.gustavohub.clean.domain.repository.TaskRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Adapter em memoria - util para testes e desenvolvimento local.
 * Ativar em application.properties: app.persistence.adapter=memory
 */
@Component
@ConditionalOnProperty(name = "app.persistence.adapter", havingValue = "memory")
public class InMemoryTaskRepository implements TaskRepository {

    private final Map<Long, Task> storage = new LinkedHashMap<>();
    private final AtomicLong sequence = new AtomicLong(1);

    @Override
    public Task save(Task task) {
        if (task.getId() == null) {
            Long newId = sequence.getAndIncrement();
            Task withId = Task.reconstitute(newId, task.getTitle(), task.getDescription(),
                    task.isCompleted(), task.getCreatedAt());
            storage.put(newId, withId);
            return withId;
        }
        storage.put(task.getId(), task);
        return task;
    }

    @Override
    public Optional<Task> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void deleteById(Long id) {
        storage.remove(id);
    }

    @Override
    public boolean existsById(Long id) {
        return storage.containsKey(id);
    }
}
