package com.ms.dlj.service;

import com.ms.dlj.mapper.OrderLineMapper;
import com.ms.dlj.orderline.OrderLine;
import com.ms.dlj.product.PurchaseRequest;
import com.ms.dlj.records.OrderLineRequest;
import com.ms.dlj.repository.OrderLineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderLineService {

    private final OrderLineRepository orderLineRepository;

    private final OrderLineMapper orderLineMapper;

    public Integer saveOrderLine(OrderLineRequest orderLineRequest) {
        OrderLine orderLine = orderLineMapper.toOrderLine(orderLineRequest);
        return orderLineRepository.save(orderLine).getId();

    }
}
