package com.gustavohub.clean.application.usecase.tasks;

import com.gustavohub.clean.domain.entity.Task;
import com.gustavohub.clean.domain.repository.TaskRepository;

public class CompleteTaskUseCase {

    private final TaskRepository taskRepository;

    public CompleteTaskUseCase(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public record Output(String id, String title, String description, boolean completed) {}

    public Output execute(String id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        task.complete();                    // regra na entidade
        Task saved = taskRepository.save(task); // save() em vez de update()
        return CreateTaskUseCase.toOutput(saved);
    }
}
