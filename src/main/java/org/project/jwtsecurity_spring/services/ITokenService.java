package org.project.jwtsecurity_spring.services;

import org.project.jwtsecurity_spring.dtos.requests.TokenRequest;
import org.project.jwtsecurity_spring.entities.Token;

public interface ITokenService {
    Token save(TokenRequest request);
    Token getById(String id);
    boolean exitById(String id);
}
