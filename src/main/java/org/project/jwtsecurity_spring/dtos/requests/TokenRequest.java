package org.project.jwtsecurity_spring.dtos.requests;

import lombok.*;
import org.project.jwtsecurity_spring.dtos.enums.EnumTokenType;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class TokenRequest {
    private String token;
    private EnumTokenType type;
}
