package com.gustavohub.clean.infrastructure.gateway;

import com.gustavohub.clean.domain.entity.User;

import java.util.List;

public interface UserRepositoryGateway {

    List<User> findAll();

}
