package com.gustavohub.clean.domain.repository;

import com.gustavohub.clean.domain.entity.Task;
import java.util.List;
import java.util.Optional;

/**
 * Porta de saída definida no domínio.
 * Implementações ficam na camada de infraestrutura.
 */
public interface TaskRepository {
    Task save(Task task);
    Optional<Task> findById(Long id);
    List<Task> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
}
