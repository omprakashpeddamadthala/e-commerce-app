package com.ms.dlj.records;

public record OrderLineRequest(
        Integer id,
        Integer orderId,
        Integer productId ,
        double quantity
) {
}
