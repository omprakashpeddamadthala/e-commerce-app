package com.ms.dlj.order;

import com.ms.dlj.customer.CustomerClient;
import com.ms.dlj.exceptions.BusinessException;
import com.ms.dlj.mapper.OrderMapper;
import com.ms.dlj.product.ProductClient;
import com.ms.dlj.product.PurchaseRequest;
import com.ms.dlj.records.OrderLineRequest;
import com.ms.dlj.repository.OrderRepository;
import com.ms.dlj.service.OrderLineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final OrderLineService orderLineService;

    private final CustomerClient customerClient;

    private final ProductClient productClient;

    private final OrderMapper orderMapper;

    public Integer createOrder(OrderRequest orderRequest) {
        var customer = customerClient.getCustomerById( orderRequest.customerId() )
                .orElseThrow(()-> new BusinessException("Cannot create order :: No customer found with provided ID :: " + orderRequest.customerId()));

        this.productClient.purchaseProducts( orderRequest.products() );
        orderRepository.save( orderMapper.toOrder( orderRequest ) );

        for( PurchaseRequest purchaseRequest : orderRequest.products() ) {
            orderLineService.saveOrderLine(new OrderLineRequest(
                    null,
                    orderRequest.id(),
                    purchaseRequest.productId(),
                    purchaseRequest.quantity() )
            );
        }

        return 1;
    }
}
