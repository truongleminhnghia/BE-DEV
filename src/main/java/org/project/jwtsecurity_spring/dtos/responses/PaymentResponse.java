package org.project.jwtsecurity_spring.dtos.responses;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {

    private String status;
    private String message;
    private String url;
}
