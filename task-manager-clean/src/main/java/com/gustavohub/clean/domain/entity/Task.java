package com.gustavohub.clean.domain.entity;

import java.time.LocalDateTime;

/**
 * Entidade de domínio PURA — zero dependências de framework.
 * Toda regra de negócio vive aqui.
 */
public class Task {

    private final Long id;
    private String title;
    private String description;
    private boolean completed;
    private final LocalDateTime createdAt;

    private Task(Long id, String title, String description,
                 boolean completed, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.createdAt = createdAt;
    }

    /** Factory: nova Task (sem ID — o banco atribui) */
    public static Task create(String title, String description) {
        validate(title);
        return new Task(null, title.trim(), description, false, LocalDateTime.now());
    }

    /** Factory: reconstitui do banco */
    public static Task reconstitute(Long id, String title, String description,
                                    boolean completed, LocalDateTime createdAt) {
        return new Task(id, title, description, completed, createdAt);
    }

    /** Regra: não pode completar duas vezes */
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

    private static void validate(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Task title cannot be blank.");
        }
        if (title.length() > 255) {
            throw new IllegalArgumentException("Task title cannot exceed 255 characters.");
        }
    }

    public Long getId()                { return id; }
    public String getTitle()           { return title; }
    public String getDescription()     { return description; }
    public boolean isCompleted()       { return completed; }
    public LocalDateTime getCreatedAt(){ return createdAt; }
}
