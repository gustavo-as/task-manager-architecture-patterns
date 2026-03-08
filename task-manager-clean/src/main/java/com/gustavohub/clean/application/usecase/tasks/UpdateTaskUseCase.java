package com.gustavohub.clean.application.usecase.tasks;

import com.gustavohub.clean.domain.entity.Task;
import com.gustavohub.clean.domain.repository.TaskRepository;

public class UpdateTaskUseCase {

    private final TaskRepository taskRepository;

    public UpdateTaskUseCase(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public record Input(Long id, String title, String description) {}

    public record Output(Long id, String title, String description, boolean completed) {}

    public Output execute(Input input) {
        Task task = taskRepository.findById(input.id())
                .orElseThrow(() -> new TaskNotFoundException(input.id()));
        task.update(input.title(), input.description());
        Task saved = taskRepository.save(task);
        return new Output(saved.getId(), saved.getTitle(), saved.getDescription(), saved.isCompleted());
    }
}
