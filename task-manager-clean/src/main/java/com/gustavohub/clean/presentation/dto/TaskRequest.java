package com.gustavohub.clean.presentation.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequest {

    private String title;
    private String description;
}
