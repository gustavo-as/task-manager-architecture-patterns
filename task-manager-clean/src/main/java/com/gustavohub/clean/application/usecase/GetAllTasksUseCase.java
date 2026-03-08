package com.gustavohub.clean.application.usecase;

import com.gustavohub.clean.domain.entity.Task;
import com.gustavohub.clean.domain.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllTasksUseCase {

    private final TaskRepository taskRepository;

    public List<Task> execute() {
        return taskRepository.findAll();
    }
}
