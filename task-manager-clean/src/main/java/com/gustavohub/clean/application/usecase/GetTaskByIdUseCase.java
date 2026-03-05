package com.gustavohub.clean.application.usecase;

import com.gustavohub.clean.domain.entity.Task;
import com.gustavohub.clean.domain.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetTaskByIdUseCase {

    private final TaskRepository taskRepository;

    public Optional<Task> execute(String id) {
        return taskRepository.findById(id);
    }
}
