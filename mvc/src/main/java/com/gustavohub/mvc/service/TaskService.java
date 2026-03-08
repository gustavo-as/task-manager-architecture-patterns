package com.gustavohub.mvc.service;

import com.gustavohub.mvc.model.Task;
import com.gustavohub.mvc.repository.TaskRepository;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    //    private final JavaMailSender  mailSender;   // ← infra de e-mail
    //    private final S3Client        s3Client;

    private final Task task = new Task();

    @Cacheable(value = "tasks", key = "#id")    // ← detalhe de cache (Redis/EhCache)
    public Task findById(Long id) {
        return this.task;
    }

    @CacheEvict(value = "tasks", key = "#id")
    public Task completeTask(Long id) {
        // regra de negócio + invalidação de cache + envio de e-mail no mesmo método
        task.setCompleted(true);
//        mailSender.send(...);               // ← SMTP dentro do service de negócio
//        s3Client.putObject(...);            // ← AWS SDK dentro do service de negócio
        return this.task;
    }

    @Async                                      // ← thread pool gerenciada pelo Spring
    private void sendCompletionEmail() {
        // Implementacao das regras de negocio do envio de email
    }

}
