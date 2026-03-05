package com.gustavohub.clean.application.usecase;

import com.gustavohub.clean.domain.entity.Task;
import com.gustavohub.clean.domain.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateTaskUseCase {

    private final TaskRepository taskRepository;

    public Task execute(String title, String description) {
        Task task = new Task(title, description);
        return taskRepository.save(task);
    }
}
