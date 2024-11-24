package org.project.jwtsecurity_spring.dtos.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder

public class CreateProductRequest {

    @NotNull(message = "PRODUCT_NAME_NOT_NULL")
    @NotBlank(message = "PRODUCT_NAME_NOT_BLANK")
    private String name;

    @NotNull(message = "PRICE_NOT_NULL")
  //  @NotBlank(message = "PRICE_NOT_BLANK")
    @Min(value = 0, message = "PRICE_INVALID")
    private double price;

    @NotNull(message = "QUANTITY_NOT_NULL")
  //  @NotBlank(message = "QUANTITY_NOT_BLANK")
    @Min(value = 0, message = "QUANTITY_INVALID")
    private int quantity;

    private String description;
}
