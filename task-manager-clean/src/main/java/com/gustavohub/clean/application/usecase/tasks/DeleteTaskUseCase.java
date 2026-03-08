package com.gustavohub.clean.application.usecase.tasks;

import com.gustavohub.clean.domain.repository.TaskRepository;

public class DeleteTaskUseCase {


    private final TaskRepository taskRepository;

    public DeleteTaskUseCase(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void execute(String id) {
        // lança exceção de domínio em vez de retornar boolean
        taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        taskRepository.deleteById(id);
    }
}
