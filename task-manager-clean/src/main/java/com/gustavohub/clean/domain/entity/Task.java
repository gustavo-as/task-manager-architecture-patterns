package com.gustavohub.clean.domain.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class Task {

    private String id;
    private String title;
    private String description;
    private boolean completed;
    private LocalDateTime createdAt;

    public Task(String title, String description) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.completed = false;
        this.createdAt = LocalDateTime.now();
    }

    public Task(String id, String title, String description, boolean completed, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.createdAt = createdAt;
    }

    public void complete() {
        this.completed = true;
    }

    public void update(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public boolean isCompleted() { return completed; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}