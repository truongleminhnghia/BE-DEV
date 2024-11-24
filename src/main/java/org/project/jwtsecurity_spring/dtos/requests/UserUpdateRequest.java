package org.project.jwtsecurity_spring.dtos.requests;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserUpdateRequest {
    private String firstName;
    private String lastName;
    private String phone;
}
