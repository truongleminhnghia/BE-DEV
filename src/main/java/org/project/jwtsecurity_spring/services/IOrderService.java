package org.project.jwtsecurity_spring.services;

import org.project.jwtsecurity_spring.dtos.requests.OrderRequest;
import org.project.jwtsecurity_spring.entities.Order;

import java.util.List;

public interface IOrderService {
    Order save(OrderRequest request);
    Order getById(String id);
    List<Order> getAllOrders();
    List<Order> getByUser(String userId);
}
