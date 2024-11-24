package org.project.jwtsecurity_spring.dtos.requests;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AuthenticateRequest {
    private String username;
    private String password;
}
