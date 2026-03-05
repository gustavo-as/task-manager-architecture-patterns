package com.gustavohub.clean.application.usecase;

import com.gustavohub.clean.domain.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteTaskUseCase {

    private final TaskRepository taskRepository;

    public boolean execute(String id) {
        if (taskRepository.findById(id).isEmpty()) {
            return false;
        }
        taskRepository.deleteById(id);
        return true;
    }
}
