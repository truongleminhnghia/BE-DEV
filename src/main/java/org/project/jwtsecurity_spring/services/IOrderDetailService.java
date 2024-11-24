package org.project.jwtsecurity_spring.services;

import org.project.jwtsecurity_spring.dtos.requests.OrderDetailRequest;
import org.project.jwtsecurity_spring.dtos.responses.OrderDetailResponse;
import org.project.jwtsecurity_spring.entities.OrderDetail;

import java.util.List;

public interface IOrderDetailService {
    OrderDetail save(OrderDetailRequest request, String orderId);
    OrderDetailResponse findById(String id);
    List<OrderDetailResponse> getByOrderId(String orderId);
    List<OrderDetailResponse> getAll();
}
