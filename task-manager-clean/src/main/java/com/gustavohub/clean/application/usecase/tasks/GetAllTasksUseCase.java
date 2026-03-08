package com.gustavohub.clean.application.usecase.tasks;

import com.gustavohub.clean.domain.repository.TaskRepository;

import java.util.List;

public class GetAllTasksUseCase {

    private final TaskRepository taskRepository;

    public GetAllTasksUseCase(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public record Output(Long id, String title, String description, boolean completed) {}

    public List<Output> execute() {
        return taskRepository.findAll().stream()
                .map(t -> new Output(
                        t.getId(),
                        t.getTitle(),
                        t.getDescription(),
                        t.isCompleted()
                        )
                ).toList();
    }
}
