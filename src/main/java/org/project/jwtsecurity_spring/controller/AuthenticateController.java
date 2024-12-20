package org.project.jwtsecurity_spring.controller;

import jakarta.validation.Valid;
import org.project.jwtsecurity_spring.configs.CustomerUserDetail;
import org.project.jwtsecurity_spring.dtos.requests.AuthenticateRequest;
import org.project.jwtsecurity_spring.dtos.requests.TokenRequest;
import org.project.jwtsecurity_spring.dtos.requests.UserRegisterRequest;
import org.project.jwtsecurity_spring.dtos.responses.ApiResponse;
import org.project.jwtsecurity_spring.dtos.responses.UserResponse;
import org.project.jwtsecurity_spring.entities.Token;
import org.project.jwtsecurity_spring.entities.User;
import org.project.jwtsecurity_spring.jwt.JwtService;
import org.project.jwtsecurity_spring.mapper.UserMapper;
import org.project.jwtsecurity_spring.services.ITokenService;
import org.project.jwtsecurity_spring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;

@RestController
@RequestMapping("/auths")
@CrossOrigin(origins = "*")
public class AuthenticateController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserService userService;
    @Autowired private ITokenService tokenService;
    @Autowired private UserMapper userMapper;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> authenticate(@RequestBody AuthenticateRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(
                    (CustomerUserDetail) userDetailsService.loadUserByUsername(request.getUsername())
            );
            return ResponseEntity.ok(new ApiResponse(200, "Success", token));
        } else {
            return ResponseEntity.ok(new ApiResponse(200, "failed", null));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> createUser(@Valid @RequestBody UserRegisterRequest request) {
         User user = userService.save(request);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(400, "Failed", null));
        }
        UserResponse userResponse = userMapper.toUserResponse(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(201, "Success", userResponse));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout(@RequestBody TokenRequest tokenRequest) {
        Token token = tokenService.save(tokenRequest);
        if (token != null) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, "Success", null));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(400, "Failed", null));
    }

    @GetMapping("/verify_token")
    public ResponseEntity<ApiResponse> verifyToken(@RequestBody TokenRequest request) {
        boolean result = true;
        result = jwtService.isTokenValid(request.getToken());
        if(result) return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, "Success", null));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(400, "Failed", null));
    }

    @PostMapping("/refresh_token")
    public ResponseEntity<ApiResponse> refreshToken(@RequestBody TokenRequest request) {
        String refreshToke = jwtService.refreshToken(request);
        if (refreshToke != null) return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, "Success", refreshToke));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(400, "failed", null));
    }


}
