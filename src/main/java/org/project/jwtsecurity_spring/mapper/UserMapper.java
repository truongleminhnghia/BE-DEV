package org.project.jwtsecurity_spring.mapper;

import org.mapstruct.Mapper;
import org.project.jwtsecurity_spring.dtos.requests.UserRegisterRequest;
import org.project.jwtsecurity_spring.dtos.responses.UserResponse;
import org.project.jwtsecurity_spring.entities.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserRegisterRequest request);

    UserResponse toUserResponse(User user);

    List<UserResponse> toUserResponses(List<User> users);
}
