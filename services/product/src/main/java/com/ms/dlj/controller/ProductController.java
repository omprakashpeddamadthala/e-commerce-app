package com.ms.dlj.controller;

import com.ms.dlj.records.ProductPurchaseRequest;
import com.ms.dlj.records.ProductPurchaseResponse;
import com.ms.dlj.records.ProductRequest;
import com.ms.dlj.records.ProductResponse;
import com.ms.dlj.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Integer> createProduct(@RequestBody @Valid ProductRequest request) {
        log.info( "Received POST request to create product " );
        return ResponseEntity.ok(productService.createProduct(request));
    }

    @PostMapping("/purchase")
    public ResponseEntity<List<ProductPurchaseResponse>> purchaseProducts(List<ProductPurchaseRequest> productPurchaseRequests) {
        log.info( "Received POST request to purchase product " );
        return ResponseEntity.ok(productService.purchaseProducts(productPurchaseRequests));
    }

    @GetMapping("/productId")
    public ResponseEntity<ProductResponse> findProductById(@PathVariable Integer productId ) {
        log.info( "Received GET request to find productId ",productId );
        return ResponseEntity.ok(productService.findProductById(productId));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts(){
        log.info("Received GET request to get all products");
        return ResponseEntity.ok(productService.getAllProducts());
    }

}
