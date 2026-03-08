package com.gustavohub.clean.application.usecase.tasks;

import com.gustavohub.clean.domain.entity.Task;
import com.gustavohub.clean.domain.repository.TaskRepository;

public class GetTaskByIdUseCase {

    private final TaskRepository taskRepository;

    public GetTaskByIdUseCase(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public record Output(Long id, String title, String description, boolean completed) {}

    public Output execute(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        return new Output(task.getId(), task.getTitle(), task.getDescription(), task.isCompleted());
    }
}
