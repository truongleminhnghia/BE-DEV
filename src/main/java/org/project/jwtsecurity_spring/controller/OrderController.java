package org.project.jwtsecurity_spring.controller;

import org.project.jwtsecurity_spring.dtos.requests.OrderRequest;
import org.project.jwtsecurity_spring.dtos.responses.ApiResponse;
import org.project.jwtsecurity_spring.entities.Order;
import org.project.jwtsecurity_spring.services.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "*")
public class OrderController {
    @Autowired private IOrderService orderService;

//    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> create(@RequestBody OrderRequest request) {
        Order order = orderService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(201, "Success", order));
    }
}
