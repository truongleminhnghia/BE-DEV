package org.project.jwtsecurity_spring.dtos.responses;

import lombok.*;
import org.project.jwtsecurity_spring.entities.Role;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UserResponse {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private Role role;
}
