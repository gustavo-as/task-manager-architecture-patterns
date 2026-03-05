package com.gustavohub.clean.infrastructure.config;

import com.gustavohub.clean.domain.repository.TaskRepository;
import com.gustavohub.clean.infrastructure.repository.InMemoryTaskRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public TaskRepository taskRepository() {
        return new InMemoryTaskRepository();
    }
}
