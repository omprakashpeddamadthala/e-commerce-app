package com.ms.dlj.order;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public ResponseEntity<Integer> createOrder(@RequestBody @Valid OrderRequest orderRequest) {
        log.info( "Received POST request to create order " );
        return ResponseEntity.ok(orderService.createOrder(orderRequest));
    }
}
