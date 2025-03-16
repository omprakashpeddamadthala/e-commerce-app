package com.ms.dlj.orderline;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderLineService {

    private final OrderLineRepository orderLineRepository;

    private final OrderLineMapper orderLineMapper;

    public Integer saveOrderLine(OrderLineRequest orderLineRequest) {
        OrderLine orderLine = orderLineMapper.toOrderLine(orderLineRequest);
        return orderLineRepository.save(orderLine).getId();

    }

    public List<OrderLineResponse> getOrderLinesByOrderId(String orderId) {
        return orderLineRepository.findAllByOrderId(Integer.parseInt(orderId))
                .stream().map(orderLineMapper::toOrderLineResponse).toList();

    }
}
