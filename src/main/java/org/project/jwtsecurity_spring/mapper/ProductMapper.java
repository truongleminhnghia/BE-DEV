package org.project.jwtsecurity_spring.mapper;

import org.mapstruct.Mapper;
import org.project.jwtsecurity_spring.dtos.requests.CreateProductRequest;
import org.project.jwtsecurity_spring.dtos.responses.ProductResponse;
import org.project.jwtsecurity_spring.entities.Product;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toProduct(CreateProductRequest request);

    ProductResponse toProductResponse(Product product);

    List<ProductResponse> toProductResponses(List<Product> products);
}
