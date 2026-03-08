package com.gustavohub.clean.application.usecase.tasks;

import com.gustavohub.clean.domain.entity.Task;
import com.gustavohub.clean.domain.repository.TaskRepository;

public class GetTaskByIdUseCase {

    private final TaskRepository taskRepository;

    public GetTaskByIdUseCase(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public record Output(String id, String title, String description, boolean completed) {}

    public Output execute(String id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        return CreateTaskUseCase.toOutput(task);
    }
}
