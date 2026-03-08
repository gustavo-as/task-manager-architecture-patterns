package com.gustavohub.clean.application.usecase.tasks;

import com.gustavohub.clean.domain.repository.TaskRepository;

import java.util.List;

public class GetAllTasksUseCase {

    private final TaskRepository taskRepository;

    public GetAllTasksUseCase(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public record Output(String id, String title, String description, boolean completed) {}

    public List<CreateTaskUseCase.Output> execute() {
        return taskRepository.findAll().stream()
                .map(CreateTaskUseCase::toOutput)
                .toList();
    }
}
