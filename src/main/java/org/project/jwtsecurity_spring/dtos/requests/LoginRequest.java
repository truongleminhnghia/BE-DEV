package org.project.jwtsecurity_spring.dtos.requests;


import jakarta.annotation.security.DenyAll;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class LoginRequest {
    private String username;
    private String email;
    private String password;
}
