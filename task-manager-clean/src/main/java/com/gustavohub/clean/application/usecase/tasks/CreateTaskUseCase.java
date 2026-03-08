package com.gustavohub.clean.application.usecase.tasks;

import com.gustavohub.clean.domain.entity.Task;
import com.gustavohub.clean.domain.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// Sem @Service — a camada de aplicação não conhece o Spring
// O wiring é feito no BeanConfiguration (infrastructure/config)
public class CreateTaskUseCase {

    private final TaskRepository taskRepository;

    public CreateTaskUseCase(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public record Input(String title, String description) {}


    public record Output(String id, String title, String description, boolean completed) {}

    public Output execute(Input input) {
        // Usa factory method — validação acontece no domínio
        Task task = Task.create(input.title(), input.description());
        Task saved = taskRepository.save(task);
        return toOutput(saved);
    }

    static Output toOutput(Task task) {
        return new Output(task.getId(), task.getTitle(), task.getDescription(), task.isCompleted());
    }

}
