package org.project.jwtsecurity_spring.dtos.responses;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.project.jwtsecurity_spring.dtos.enums.EnumStatusOrder;
import org.project.jwtsecurity_spring.entities.User;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class OrderResponse {
    private String id;
    private double totalPrice;
    private int quantity;
    @Enumerated(EnumType.STRING)
    private EnumStatusOrder status;
    private String date;
    private User user;
}
