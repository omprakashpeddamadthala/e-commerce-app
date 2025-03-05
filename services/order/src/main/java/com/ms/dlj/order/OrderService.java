package com.ms.dlj.order;

import com.ms.dlj.customer.CustomerClient;
import com.ms.dlj.exceptions.BusinessException;
import com.ms.dlj.product.ProductClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final CustomerClient customerClient;

    private final ProductClient productClient;

    public Integer createOrder(OrderRequest orderRequest) {
        var customer = customerClient.getCustomerById( orderRequest.customerId() )
                .orElseThrow(()-> new BusinessException("Cannot create order :: No customer found with provided ID :: " + orderRequest.customerId()));

        this.productClient.purchaseProducts( orderRequest.products() );

        return 1;
    }
}
