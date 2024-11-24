package org.project.jwtsecurity_spring.dtos.responses;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor

public class TransactionStatusRes {
    private String status;
    private String message;
    private String data;
}
