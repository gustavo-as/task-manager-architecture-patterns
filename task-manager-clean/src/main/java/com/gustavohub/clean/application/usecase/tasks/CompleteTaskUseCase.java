package com.gustavohub.clean.application.usecase.tasks;

import com.gustavohub.clean.domain.entity.Task;
import com.gustavohub.clean.domain.repository.TaskRepository;

public class CompleteTaskUseCase {

    private final TaskRepository taskRepository;

    public CompleteTaskUseCase(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public record Output(Long id, String title, String description, boolean completed) {}

    public Output execute(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        task.complete();
        Task saved = taskRepository.save(task);
        return new Output(saved.getId(), saved.getTitle(), saved.getDescription(), saved.isCompleted());
    }
}
