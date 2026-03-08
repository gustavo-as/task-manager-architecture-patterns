package com.gustavohub.clean.domain.entity;

import com.gustavohub.clean.infrastructure.repository.jpa.UserEntity;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.UUID;

public class User {

    private Long id;
    private String name;
    private String email;

    public User(String name, String email) {
        this.id = Long.getLong(UUID.randomUUID().toString());
        this.name = name;
        this.email = email;
    }

    public User(String id, String name, String email, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public static User toUsuario(UserEntity user) {
        return new User(
                user.getName(),
                user.getEmail()
        );
    }

    public void update(String title, String description) {
        this.name = title;
        this.email = description;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
}