package org.project.jwtsecurity_spring.controller;

import org.project.jwtsecurity_spring.dtos.enums.Type_Login;
import org.project.jwtsecurity_spring.dtos.requests.Oauth2Request;
import org.project.jwtsecurity_spring.dtos.responses.ApiResponse;
import org.project.jwtsecurity_spring.services.IOauth2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/oauth2")
public class Oauth2Controller {

    @Autowired private IOauth2Service oauth2Service;

    @GetMapping("/login-social/{type_login}")
    public ResponseEntity<ApiResponse> loginSocail(@PathVariable("type_login") String type) throws UnsupportedEncodingException {
        String typeToUpperCase = type.toUpperCase();
        String urlRes = oauth2Service.generateUrl(typeToUpperCase);
        if  (urlRes == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(400, "failed", null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, "success", urlRes));
    }

    @GetMapping("/callback")
    public ResponseEntity<ApiResponse> callbackAuthenticate(
            @RequestParam("code") String code,
            @RequestParam("type_login") String type) throws IOException {
        Map<String, Object> infoUser = oauth2Service.authenticateAndFetchProfile(code, type);
        if(infoUser == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(400, "failed", null));
        }
        if (type.trim().toUpperCase().equals(Type_Login.GOOGLE.name())) {
            Oauth2Request oauth2Request = Oauth2Request.builder()
                    .fullName((String) Objects.requireNonNullElse(infoUser.get("name"), ""))
                    .googleAccountId((String) Objects.requireNonNullElse(infoUser.get("sub"), ""))
                    .password("")
                    .email((String) Objects.requireNonNullElse(infoUser.get("email"), ""))
                    .avatar((String) Objects.requireNonNullElse(infoUser.get("picture"), ""))
                    .phoneNumber("")
                    .facebookAccountId("")
                    .build();
            String result = oauth2Service.loginOauth2(oauth2Request);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, "success", result));
        }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(400, "failed", null));
    }
}
