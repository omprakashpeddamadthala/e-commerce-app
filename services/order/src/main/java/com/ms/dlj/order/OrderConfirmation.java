package com.ms.dlj.order;

import com.ms.dlj.customer.CustomerResponse;
import com.ms.dlj.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal toatalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse response,
        List<PurchaseResponse> products
) {
}
