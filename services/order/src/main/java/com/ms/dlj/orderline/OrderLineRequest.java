package com.ms.dlj.orderline;

public record OrderLineRequest(
        Integer id,
        Integer orderId,
        Integer productId ,
        double quantity
) {
}
