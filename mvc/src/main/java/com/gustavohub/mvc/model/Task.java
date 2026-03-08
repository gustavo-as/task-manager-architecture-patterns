package com.gustavohub.mvc.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    private Long id;
    private String title;
    private String description;
    private boolean completed;
    private LocalDateTime createdAt;

}
