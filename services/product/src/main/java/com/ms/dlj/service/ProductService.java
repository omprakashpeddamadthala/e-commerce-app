package com.ms.dlj.service;

import com.ms.dlj.records.ProductPurchaseRequest;
import com.ms.dlj.records.ProductPurchaseResponse;
import com.ms.dlj.records.ProductRequest;
import com.ms.dlj.records.ProductResponse;

import java.util.List;

public interface ProductService {
    Integer createProduct(ProductRequest request);

    List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> request);

    ProductResponse findProductById(Integer productId);

    List<ProductResponse> getAllProducts();
}
