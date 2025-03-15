package com.ms.dlj.mapper;

import com.ms.dlj.order.Order;
import com.ms.dlj.order.OrderRequest;
import com.ms.dlj.order.OrderResponse;
import org.springframework.stereotype.Service;

@Service
public class OrderMapper {

    public Order toOrder(OrderRequest orderRequest) {
        return Order.builder()
                .id(orderRequest.id())
                .customerId(orderRequest.customerId())
                .reference( orderRequest.reference() )
                .totalAmount( orderRequest.totalAmount() )
                .paymentMethod( orderRequest.paymentMethod() )
                .build();
    }

    public OrderResponse fromOrder(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getReference(),
                order.getTotalAmount(),
                order.getPaymentMethod(),
                order.getCustomerId()
        );
    }
}
