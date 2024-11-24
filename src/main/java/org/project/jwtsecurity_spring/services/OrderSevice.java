package org.project.jwtsecurity_spring.services;

import org.project.jwtsecurity_spring.dtos.enums.EnumStatusOrder;
import org.project.jwtsecurity_spring.dtos.enums.ErrorCode;
import org.project.jwtsecurity_spring.dtos.requests.OrderDetailRequest;
import org.project.jwtsecurity_spring.dtos.requests.OrderRequest;
import org.project.jwtsecurity_spring.entities.Order;
import org.project.jwtsecurity_spring.entities.OrderDetail;
import org.project.jwtsecurity_spring.entities.User;
import org.project.jwtsecurity_spring.exception.AppException;
import org.project.jwtsecurity_spring.mapper.OrderMapper;
import org.project.jwtsecurity_spring.repositories.OrderRepository;
import org.project.jwtsecurity_spring.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderSevice implements IOrderService {

    @Autowired private OrderRepository orderRepository;
    @Autowired private IOrderDetailService orderDetailService;
    @Autowired private OrderMapper orderMapper;
    @Autowired private UserRepository userRepository;

    @Override
    public Order save(OrderRequest request) {
        LocalDateTime date = LocalDateTime.now();
        Order order = orderMapper.toOrder(request);
        order.setStatus(EnumStatusOrder.PENDING);
        order.setDate(date);
        User userExisting = userRepository.findById(request.getUserId()).orElseThrow(() -> new AppException(ErrorCode.USER_DOES_NOT_EXIST));
        order.setUser(userExisting);
        orderRepository.save(order);
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (OrderDetailRequest detail : request.getOrderDetail()) {
            OrderDetail orderDetail = orderDetailService.save(detail, order.getId());
            orderDetails.add(orderDetail);
        }
        return order;
    }

    @Override
    public Order getById(String id) {
        return orderRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ORDER_DO_NOT_EXIST));
    }

    @Override
    public List<Order> getAllOrders() {
        return List.of();
    }

    @Override
    public List<Order> getByUser(String userId) {
        return List.of();
    }
}
