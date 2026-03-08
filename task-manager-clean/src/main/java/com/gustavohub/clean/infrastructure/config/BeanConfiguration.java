package com.gustavohub.clean.infrastructure.config;

import com.gustavohub.clean.application.usecase.tasks.*;
import com.gustavohub.clean.domain.repository.TaskRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Composition Root: registra use cases como beans Spring.
 * Use cases sao POJOs - o Spring so os conhece aqui.
 */
@Configuration
public class BeanConfiguration {

    @Bean
    public CreateTaskUseCase createTaskUseCase(TaskRepository r) {
        return new CreateTaskUseCase(r);
    }

    @Bean
    public CompleteTaskUseCase completeTaskUseCase(TaskRepository r) {
        return new CompleteTaskUseCase(r); }

    @Bean
    public UpdateTaskUseCase   updateTaskUseCase  (TaskRepository r) {
        return new UpdateTaskUseCase(r); }

    @Bean
    public GetTaskByIdUseCase  getTaskByIdUseCase (TaskRepository r) {
        return new GetTaskByIdUseCase(r); }

    @Bean
    public GetAllTasksUseCase  getAllTasksUseCase (TaskRepository r) {
        return new GetAllTasksUseCase(r); }

    @Bean
    public DeleteTaskUseCase   deleteTaskUseCase  (TaskRepository r) {
        return new DeleteTaskUseCase(r); }
}
