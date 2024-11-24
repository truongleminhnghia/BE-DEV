package org.project.jwtsecurity_spring.dtos.requests;


import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class OrderDetailRequest {

    private double price;
    private int quantity;
    private String productId;
}
