package org.project.jwtsecurity_spring.controller;

import org.project.jwtsecurity_spring.dtos.responses.ApiResponse;
import org.project.jwtsecurity_spring.dtos.responses.ProductResponse;
import org.project.jwtsecurity_spring.entities.Product;
import org.project.jwtsecurity_spring.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/main")
@CrossOrigin(origins = "*")
public class MainController {
    @Autowired
    private ProductService productService;

    @GetMapping("/product/{product_id}")
    public ResponseEntity<ApiResponse> getById(@PathVariable("product_id") String id) {
        Product productResponse = productService.getProduct(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, "success", productResponse));
    }

    @GetMapping("/products")
    public ResponseEntity<ApiResponse> getAll() {
        List<ProductResponse> productResponses = productService.getAllProducts();
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(200, "success", productResponses));
    }
}
