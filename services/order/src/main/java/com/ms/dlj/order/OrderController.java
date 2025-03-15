package com.ms.dlj.order;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Integer> createOrder(@RequestBody @Valid OrderRequest orderRequest) {
        log.info( "Received POST request to create order " );
        return ResponseEntity.ok( orderService.createOrder( orderRequest ) );
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        log.info( "Received GET request to get all orders " );
        return ResponseEntity.ok( orderService.getAllOrders() );
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable String orderId) {
        log.info( "Received GET request to get order by orderId {}", orderId );
        return ResponseEntity.ok( orderService.getOrderById( orderId ) );
    }
}
