package org.project.jwtsecurity_spring.mapper;

import org.mapstruct.Mapper;
import org.project.jwtsecurity_spring.dtos.requests.OrderDetailRequest;
import org.project.jwtsecurity_spring.dtos.responses.OrderDetailResponse;
import org.project.jwtsecurity_spring.entities.OrderDetail;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper {

    OrderDetail toOrrderDetail(OrderDetailRequest request);
    OrderDetailResponse toOrderDetailResponse(OrderDetail orderDetail);
}
