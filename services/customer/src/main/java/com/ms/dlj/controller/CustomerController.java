package com.ms.dlj.controller;

import com.ms.dlj.records.CustomerRequest;
import com.ms.dlj.records.CustomerResponse;
import com.ms.dlj.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
@Slf4j
public class CustomerController {

    public final CustomerService customerService;

    @PostMapping
    public ResponseEntity<String> createCustomer(@RequestBody @Valid CustomerRequest customerRequest) {
        log.info( "Received POST request to create customer " );
        return ResponseEntity.ok(customerService.createCustomer(customerRequest));
    }

    @PutMapping
    public ResponseEntity<Void> updateCustomer(@RequestBody @Valid CustomerRequest customerRequest) {
        log.info( "Received PUT request to update customer " );
        customerService.updateCustomer(customerRequest);
        return ResponseEntity.accepted().build();
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        log.info("Received GET request to get all customers");
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable String customerId) {
        log.info("Received GET request to get customer by customerId {}", customerId);
        return ResponseEntity.ok(customerService.getCustomerById(customerId));
    }

    @GetMapping("/exits/{customerId}")
    public ResponseEntity<Boolean> existsCustomerById(@PathVariable String customerId) {
        log.info("Received GET request to check if customer exists by customerId {}", customerId);
        return ResponseEntity.ok(customerService.existsCustomerById(customerId));
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomerById(@PathVariable String customerId) {
        log.info("Received DELETE request to delete customer by customerId {}", customerId);
        customerService.deleteCustomerById(customerId);
        return ResponseEntity.accepted().build();
    }
}
