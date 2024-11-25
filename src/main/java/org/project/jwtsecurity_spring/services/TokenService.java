package org.project.jwtsecurity_spring.services;

import org.project.jwtsecurity_spring.dtos.enums.ErrorCode;
import org.project.jwtsecurity_spring.dtos.requests.TokenRequest;
import org.project.jwtsecurity_spring.entities.Token;
import org.project.jwtsecurity_spring.exception.AppException;
import org.project.jwtsecurity_spring.jwt.JwtService;
import org.project.jwtsecurity_spring.mapper.TokenMapper;
import org.project.jwtsecurity_spring.repositories.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService implements ITokenService {

    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private TokenMapper tokenMapper;
    @Autowired
    private JwtService jwtService;

    @Override
    public Token save(TokenRequest request) {
        boolean result = true;
        result = jwtService.isTokenValid(request.getToken());
        Token token = new Token();
        if (result) {
            String jwtId = jwtService.extractJwtId(request.getToken());
            Date exp = jwtService.getExpiration(request.getToken());
            if (!jwtId.isEmpty() && exp != null) {
                token = tokenMapper.toToken(request);
                token.setId(jwtId);
                token.setExpires(exp);
                tokenRepository.save(token);
            }
            return tokenRepository.save(token);
        } else  {
            throw new AppException(ErrorCode.TOKEN_INVALID);
        }

    }

    @Override
    public Token getById(String id) {
        return tokenRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.TOKEN_NOT_FOUND));
    }

    @Override
    public boolean exitById(String id) {
        return tokenRepository.existsById(id);
    }
}
