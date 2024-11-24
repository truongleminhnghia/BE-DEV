package org.project.jwtsecurity_spring.mapper;

import org.mapstruct.Mapper;
import org.project.jwtsecurity_spring.dtos.requests.OrderRequest;
import org.project.jwtsecurity_spring.dtos.responses.OrderResponse;
import org.project.jwtsecurity_spring.entities.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order toOrder(OrderRequest request);
    OrderResponse toOrderResponse(Order order);
}
