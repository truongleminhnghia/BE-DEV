package org.project.jwtsecurity_spring.services;

import org.project.jwtsecurity_spring.dtos.enums.ErrorCode;
import org.project.jwtsecurity_spring.dtos.requests.OrderDetailRequest;
import org.project.jwtsecurity_spring.dtos.responses.OrderDetailResponse;
import org.project.jwtsecurity_spring.entities.Order;
import org.project.jwtsecurity_spring.entities.OrderDetail;
import org.project.jwtsecurity_spring.entities.Product;
import org.project.jwtsecurity_spring.exception.AppException;
import org.project.jwtsecurity_spring.mapper.OrderDetailMapper;
import org.project.jwtsecurity_spring.repositories.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailService implements IOrderDetailService {
    @Autowired private OrderDetailRepository orderDetailRepository;
    @Autowired private OrderDetailMapper orderDetailMapper;
    @Autowired private IOrderService orderService;
    @Autowired private ProductService productService;

    @Override
    public OrderDetail save(OrderDetailRequest request, String orderId) {
        OrderDetail orderDetail = orderDetailMapper.toOrrderDetail(request);
        Product productExiting = productService.getProduct(request.getProductId());
        if (productExiting == null) throw new AppException(ErrorCode.PRODUCT_NOT_EXIST);
        orderDetail.setProduct(productExiting);
        orderDetail.setSubtotal(request.getPrice() * request.getQuantity());
        Order orderExisting = orderService.getById(orderId);
        orderDetail.setOrder(orderExisting);
        orderDetailRepository.save(orderDetail);
        return orderDetail;
    }

    @Override
    public OrderDetailResponse findById(String id) {
        return null;
    }

    @Override
    public List<OrderDetailResponse> getByOrderId(String orderId) {
        return List.of();
    }

    @Override
    public List<OrderDetailResponse> getAll() {
        return List.of();
    }
}
