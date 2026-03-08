package com.gustavohub.clean.domain.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class Task {
    private final String id;
    private String title;
    private String description;
    private boolean completed;
    private final LocalDateTime createdAt;

    // Construtor privado — ninguém cria Task diretamente
    private Task(String id, String title, String description,
                 boolean completed, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.createdAt = createdAt;
    }

    // Factory method para nova Task — aplica validações
    public static Task create(String title, String description) {
        validate(title);
        return new Task(
                UUID.randomUUID().toString(),
                title.trim(),
                description,
                false,
                LocalDateTime.now()
        );
    }

    // Factory method para reconstituir do repositório — sem re-validar
    public static Task reconstitute(String id, String title, String description,
                                    boolean completed, LocalDateTime createdAt) {
        return new Task(id, title, description, completed, createdAt);
    }

    // Regra de negócio protegida na entidade
    public void complete() {
        if (this.completed) {
            throw new IllegalStateException("Task is already completed.");
        }
        this.completed = true;
    }

    public void update(String title, String description) {
        validate(title);
        this.title = title.trim();
        this.description = description;
    }

    // Validação de domínio centralizada
    private static void validate(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Task title cannot be blank.");
        }
        if (title.length() > 255) {
            throw new IllegalArgumentException("Task title cannot exceed 255 characters.");
        }
    }

    public String getId()                { return id; }
    public String getTitle()             { return title; }
    public String getDescription()       { return description; }
    public boolean isCompleted()         { return completed; }
    public LocalDateTime getCreatedAt()  { return createdAt; }
}