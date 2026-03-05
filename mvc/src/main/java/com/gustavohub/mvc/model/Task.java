package com.gustavohub.mvc.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
}
