package com.gustavohub.clean.infrastructure.repository.jpa;

import com.gustavohub.clean.domain.entity.User;
import com.gustavohub.clean.infrastructure.gateway.UserRepositoryGateway;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRepositoryJpaGateway implements UserRepositoryGateway {

    @Override
    public List<User> findAll() {
        return User
    }
}
