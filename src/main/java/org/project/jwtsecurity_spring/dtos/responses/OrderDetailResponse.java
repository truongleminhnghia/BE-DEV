package org.project.jwtsecurity_spring.dtos.responses;

import lombok.*;
import org.project.jwtsecurity_spring.entities.Order;
import org.project.jwtsecurity_spring.entities.Product;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailResponse {
    private String id;
    private double price;
    private int quantity;
    private double subtotal;
    private Product product;
    private Order order;
}
