package org.project.jwtsecurity_spring.dtos.responses;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse {
    private int code;
    private String message;
    private Object data;
}
