package org.project.jwtsecurity_spring.services;

import org.project.jwtsecurity_spring.dtos.enums.Type_Login;
import org.project.jwtsecurity_spring.dtos.requests.Oauth2Request;
import org.project.jwtsecurity_spring.entities.User;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public interface IOauth2Service {

    public String generateUrl(String type) throws UnsupportedEncodingException;
    public Map<String, Object> authenticateAndFetchProfile(String code, String type) throws IOException;
    public String loginOauth2(Oauth2Request request);
}
