package com.ms.dlj.orderline;

import com.ms.dlj.order.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/orderlines")
public class OrderLineController {

    private final OrderService orderService;


}
