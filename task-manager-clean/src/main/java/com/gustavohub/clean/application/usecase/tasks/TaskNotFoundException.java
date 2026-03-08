package com.gustavohub.clean.application.usecase.tasks;

public class TaskNotFoundException extends RuntimeException{
    public TaskNotFoundException(String id) {
        super("Task not found: " + id);
    }
}
