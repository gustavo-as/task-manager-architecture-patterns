package com.gustavohub.clean.infrastructure.repository.jpa;

import com.gustavohub.clean.domain.entity.Task;

class TaskJpaMapper {

    static TaskJpaEntity toJpaEntity(Task task) {
        return new TaskJpaEntity(task.getId(), task.getTitle(), task.getDescription(),
                task.isCompleted(), task.getCreatedAt());
    }

    static Task toDomain(TaskJpaEntity e) {
        return Task.reconstitute(e.getId(), e.getTitle(), e.getDescription(),
                e.isCompleted(), e.getCreatedAt());
    }
}
