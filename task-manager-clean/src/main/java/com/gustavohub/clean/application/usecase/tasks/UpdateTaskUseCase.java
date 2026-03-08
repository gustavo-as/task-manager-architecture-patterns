package com.gustavohub.clean.application.usecase.tasks;

import com.gustavohub.clean.domain.entity.Task;
import com.gustavohub.clean.domain.repository.TaskRepository;

public class UpdateTaskUseCase {

    private final TaskRepository taskRepository;

    public UpdateTaskUseCase(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public record Input(String id, String title, String description) {}

    public Output execute(Input input) {
        Task task = taskRepository.findById(input.id())
                .orElseThrow(() -> new TaskNotFoundException(input.id()));

        task.update(input.title(), input.description());
        Task saved = taskRepository.save(task);

        // ✅ retorna o próprio Output, não o de CreateTaskUseCase
        return new Output(saved.getId(), saved.getTitle(), saved.getDescription(), saved.isCompleted());
    }

    public Output execute(Input input) {
        Task task = taskRepository.findById(input.id())
                .orElseThrow(() -> new TaskNotFoundException(input.id()));

        task.update(input.title(), input.description()); // ✅ regra na entidade
        Task saved = taskRepository.save(task);           // ✅ save() em vez de update()
        return new Output(saved.getId(), saved.getTitle(), saved.getDescription(), saved.isCompleted());
    }
}
