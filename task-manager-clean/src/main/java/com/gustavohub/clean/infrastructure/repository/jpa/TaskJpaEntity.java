package com.gustavohub.clean.infrastructure.repository.jpa;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
class TaskJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private boolean completed;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected TaskJpaEntity() {}

    TaskJpaEntity(Long id, String title, String description,
                  boolean completed, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.createdAt = createdAt;
    }

    Long getId()                { return id; }
    String getTitle()           { return title; }
    String getDescription()     { return description; }
    boolean isCompleted()       { return completed; }
    LocalDateTime getCreatedAt(){ return createdAt; }
}
