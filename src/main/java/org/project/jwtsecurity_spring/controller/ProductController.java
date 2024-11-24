package org.project.jwtsecurity_spring.controller;

import jakarta.validation.Valid;
import org.project.jwtsecurity_spring.dtos.requests.CreateProductRequest;
import org.project.jwtsecurity_spring.dtos.responses.ApiResponse;
import org.project.jwtsecurity_spring.dtos.responses.ProductResponse;
import org.project.jwtsecurity_spring.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "*")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createProduct(@Valid @RequestBody CreateProductRequest request) {
        ProductResponse productResponse = productService.saveProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(201, "Product created", productResponse));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{product_id}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable("product_id") String id) {
        boolean result = productService.deleteProduct(id);
        if (result) {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, "success", null));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(500, "error", null));
    }
}