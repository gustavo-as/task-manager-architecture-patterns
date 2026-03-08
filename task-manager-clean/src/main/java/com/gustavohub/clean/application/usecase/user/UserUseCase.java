package com.gustavohub.clean.application.usecase.user;

import com.gustavohub.clean.domain.entity.User;
import com.gustavohub.clean.infrastructure.gateway.UserRepositoryGateway;

import java.util.List;

public class UserUseCase {

    private final UserRepositoryGateway userRepositoryGateway;

    public UserUseCase(UserRepositoryGateway userRepositoryGateway) {
        this.userRepositoryGateway = userRepositoryGateway;
    }

    public List<User> findAll(){
        return userRepositoryGateway.findAll();
    }
}
