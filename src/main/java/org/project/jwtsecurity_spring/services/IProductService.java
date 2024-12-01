package org.project.jwtsecurity_spring.services;

import org.project.jwtsecurity_spring.dtos.requests.CreateProductRequest;
import org.project.jwtsecurity_spring.dtos.responses.ProductResponse;
import org.project.jwtsecurity_spring.entities.Product;

import java.util.List;

public interface IProductService {
    Product saveProduct(CreateProductRequest request);
    Product getProduct(String id);
    boolean deleteProduct(String id);
    List<ProductResponse> getAllProducts();
    List<ProductResponse> getByName(String name);

}
