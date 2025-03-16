package com.ms.dlj.orderline;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/order-lines")
public class OrderLineController {

    private final OrderLineService orderLineService;


    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderLineResponse>> getOrderLinesByOrderId(@PathVariable String orderId) {
        log.info( "Received GET request to get order lines by orderId {}", orderId );
        return ResponseEntity.ok( orderLineService.getOrderLinesByOrderId( orderId ) );
    }

}
