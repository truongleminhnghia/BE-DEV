package org.project.jwtsecurity_spring.services;

import org.project.jwtsecurity_spring.dtos.enums.ErrorCode;
import org.project.jwtsecurity_spring.dtos.requests.CreateProductRequest;
import org.project.jwtsecurity_spring.dtos.responses.ProductResponse;
import org.project.jwtsecurity_spring.entities.Product;
import org.project.jwtsecurity_spring.exception.AppException;
import org.project.jwtsecurity_spring.mapper.ProductMapper;
import org.project.jwtsecurity_spring.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ProductService implements IProductService {

    @Autowired private ProductRepository productRepository;
    @Autowired private ProductMapper productMapper;

    @Override
    public Product saveProduct(CreateProductRequest request) {
//        Product product = productRepository.save(productMapper.toProduct(request));
        return productRepository.save(productMapper.toProduct(request));
    }

    @Override
    public Product getProduct(String id) {
        return productRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXIST));
    }

    @Override
    public boolean deleteProduct(String id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXIST));
        productRepository.deleteById(product.getId());
        return true;
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.isEmpty() ? Collections.emptyList() : productMapper.toProductResponses(products);
    }

    @Override
    public List<ProductResponse> getByName(String name) {
        return List.of();
    }
}
