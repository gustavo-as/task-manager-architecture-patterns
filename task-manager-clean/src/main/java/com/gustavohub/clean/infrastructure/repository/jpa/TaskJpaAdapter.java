package com.gustavohub.clean.infrastructure.repository.jpa;

import com.gustavohub.clean.domain.entity.Task;
import com.gustavohub.clean.domain.repository.TaskRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@ConditionalOnProperty(name = "app.persistence.adapter", havingValue = "jpa", matchIfMissing = true)
public class TaskJpaAdapter implements TaskRepository {

    private final SpringDataTaskRepository jpaRepository;

    public TaskJpaAdapter(SpringDataTaskRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Task save(Task task) {
        TaskJpaEntity entity = TaskJpaMapper.toJpaEntity(task);
        return TaskJpaMapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public Optional<Task> findById(Long id) {
        return jpaRepository.findById(id).map(TaskJpaMapper::toDomain);
    }

    @Override
    public List<Task> findAll() {
        return jpaRepository.findAll().stream()
                .map(TaskJpaMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }
}
