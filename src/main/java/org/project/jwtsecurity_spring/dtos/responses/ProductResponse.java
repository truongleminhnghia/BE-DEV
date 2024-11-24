package org.project.jwtsecurity_spring.dtos.responses;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder

public class ProductResponse {
    private String id;
    private String name;
    private double price;
    private int quantity;
    private String description;
}
