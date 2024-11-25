package org.project.jwtsecurity_spring.mapper;

import org.mapstruct.Mapper;
import org.project.jwtsecurity_spring.dtos.requests.TokenRequest;
import org.project.jwtsecurity_spring.entities.Token;

@Mapper(componentModel = "spring")
public interface TokenMapper {
    Token toToken(TokenRequest request);
}
