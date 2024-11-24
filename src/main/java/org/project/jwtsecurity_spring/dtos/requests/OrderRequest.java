package org.project.jwtsecurity_spring.dtos.requests;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class OrderRequest {

    private double totalPrice;
    private int quantity;
    private String userId;
    private List<OrderDetailRequest> orderDetail;
}
