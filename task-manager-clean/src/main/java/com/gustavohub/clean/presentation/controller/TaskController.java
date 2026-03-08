package com.gustavohub.clean.presentation.controller;

import com.gustavohub.clean.application.usecase.tasks.*;
import com.gustavohub.clean.presentation.dto.TaskRequest;
import com.gustavohub.clean.presentation.dto.TaskResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final CreateTaskUseCase createTaskUseCase;
    private final GetAllTasksUseCase getAllTasksUseCase;
    private final GetTaskByIdUseCase getTaskByIdUseCase;
    private final UpdateTaskUseCase updateTaskUseCase;
    private final CompleteTaskUseCase completeTaskUseCase;
    private final DeleteTaskUseCase deleteTaskUseCase;

    @GetMapping
    public ResponseEntity<List<TaskResponse>> findAll() {
        List<TaskResponse> tasks = getAllTasksUseCase.execute()
                .stream()
                .map(TaskResponse::from)
                .toList();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> findById(@PathVariable String id) {
        return getTaskByIdUseCase.execute(id)
                .map(TaskResponse::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TaskResponse> create(@RequestBody TaskRequest request) {
        TaskResponse response = TaskResponse.from(
                createTaskUseCase.execute(request.getTitle(), request.getDescription())
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> update(@PathVariable String id, @RequestBody TaskRequest request) {
        return updateTaskUseCase.execute(id, request.getTitle(), request.getDescription())
                .map(TaskResponse::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<TaskResponse> complete(@PathVariable String id) {
        return completeTaskUseCase.execute(id)
                .map(TaskResponse::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (!deleteTaskUseCase.execute(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}