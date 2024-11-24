package org.project.jwtsecurity_spring.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserRegisterRequest {

    @NotBlank(message = "EMAIL_NOT_BLANK")
    @NotNull(message = "EMAIL_NOT_NULL")
    @Email(message = "EMAIL_INVALID_FORMAT")
    private String email;

    @NotBlank(message = "PASSWORD_NOT_BLANK")
    @NotNull(message = "PASSWORD_NOT_NULL")
    @Size(min = 8, message = "PASSWORD_INVALID")
    private String password;

    private String roleName;
}
