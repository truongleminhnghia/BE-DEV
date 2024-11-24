package org.project.jwtsecurity_spring.dtos.requests;

import lombok.*;
import org.project.jwtsecurity_spring.dtos.enums.EnumPaymentMethod;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequest {

    private String orderId;
    private EnumPaymentMethod paymentMethod;
}
