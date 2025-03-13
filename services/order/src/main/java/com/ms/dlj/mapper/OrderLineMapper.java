package com.ms.dlj.mapper;

import com.ms.dlj.order.Order;
import com.ms.dlj.orderline.OrderLine;
import com.ms.dlj.records.OrderLineRequest;
import org.springframework.stereotype.Service;

@Service
public class OrderLineMapper {

    public OrderLine toOrderLine(OrderLineRequest orderLineRequest) {
        return OrderLine.builder()
                .id( orderLineRequest.id() )
                .order( Order.builder()
                        .id( orderLineRequest.orderId() )
                        .build() )
                .productId( orderLineRequest.productId() )
                .quantity( orderLineRequest.quantity() )
                .build();
    }
}
