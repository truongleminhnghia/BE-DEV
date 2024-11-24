package org.project.jwtsecurity_spring.services;

import org.project.jwtsecurity_spring.dtos.requests.UserRegisterRequest;
import org.project.jwtsecurity_spring.dtos.requests.UserUpdateRequest;
import org.project.jwtsecurity_spring.dtos.responses.UserResponse;
import org.project.jwtsecurity_spring.entities.User;

import java.util.List;

public interface IUserService {
    Object save(UserRegisterRequest request);
    UserResponse getByEmail(String email);
    User getById(String id);
    List<UserResponse> getAll();
    UserResponse update(String id, UserUpdateRequest request);
    boolean delete(String id);
}
