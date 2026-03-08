package com.gustavohub.clean.application.usecase.tasks;

import com.gustavohub.clean.domain.entity.Task;
import com.gustavohub.clean.domain.repository.TaskRepository;

public class CreateTaskUseCase {

    private final TaskRepository taskRepository;

    public CreateTaskUseCase(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public record Input(String title, String description) {}

    public record Output(Long id, String title, String description, boolean completed) {}

    public Output execute(Input input) {
        Task task  = Task.create(input.title(), input.description());
        Task saved = taskRepository.save(task);
        return toOutput(saved);
    }

    static Output toOutput(Task task) {
        return new Output(task.getId(), task.getTitle(), task.getDescription(), task.isCompleted());
    }
}
