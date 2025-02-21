package com.ms.dlj.service.impl;

import com.ms.dlj.exceptions.ProductNotFoundException;
import com.ms.dlj.exceptions.ProductPurchaseException;
import com.ms.dlj.mapper.ProductMapper;
import com.ms.dlj.model.Product;
import com.ms.dlj.records.ProductPurchaseRequest;
import com.ms.dlj.records.ProductPurchaseResponse;
import com.ms.dlj.records.ProductRequest;
import com.ms.dlj.records.ProductResponse;
import com.ms.dlj.repository.CategoryRepository;
import com.ms.dlj.repository.ProductRepository;
import com.ms.dlj.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final ProductMapper productMapper;

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        Product product = productMapper.toProduct(productRequest);
        return productRepository.save(product).getId();
    }

/*    @Override
    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> productPurchaseRequests) {
        List<Integer> productIds = productPurchaseRequests.stream()
                .map(ProductPurchaseRequest::productId)
                .collect(Collectors.toList());

        List<Product> storedProducts = productRepository.findAllByIdInOrderById(productIds);

        if(productIds.size() == storedProducts.size()) {
            throw new ProductPurchaseException("One or more products does not exist");
        }

        List<ProductPurchaseResponse> responseList =new ArrayList<ProductPurchaseResponse>();
        Double newAvailableQuantity = 0.0;
        for (int i = 0; i < storedProducts.size(); i++) {
            Product storedProduct = storedProducts.get(i);
            ProductPurchaseRequest productPurchaseRequest = productPurchaseRequests.get(i);
            if(storedProduct.getAvailableQuantity() < productPurchaseRequest.quantity()) {
                throw new ProductPurchaseException("Insufficient stock quantity for product " + storedProduct.getName());
            }
            newAvailableQuantity = storedProduct.getAvailableQuantity() - productPurchaseRequest.quantity();
            storedProduct.setAvailableQuantity(newAvailableQuantity);
            productRepository.save(storedProduct);
            ProductPurchaseResponse response = productMapper.toProductPurchaseResponse(storedProduct,newAvailableQuantity);
            responseList.add(response);

        }


        return responseList;
    }*/

    @Override
    @Transactional
    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> productPurchaseRequests) {
        if (productPurchaseRequests.isEmpty()) {
            return Collections.emptyList();
        }

        // Extract product IDs
        List<Integer> productIds = productPurchaseRequests.stream()
                .map(ProductPurchaseRequest::productId)
                .distinct()
                .collect(Collectors.toList());

        // Find all products in one batch operation
        List<Product> storedProducts = productRepository.findAllByIdInOrderById(productIds);

        // Validate all products exist
        validateProductsExist(productIds, storedProducts);

        // Process all purchases atomically
        List<ProductPurchaseResponse> responses = new ArrayList<>();

        for (ProductPurchaseRequest request : productPurchaseRequests) {
            Product storedProduct = storedProducts.stream()
                    .filter(p -> p.getId().equals(request.productId()))
                    .findFirst()
                    .orElseThrow(() -> new ProductPurchaseException("Product not found"));

            validateStockAvailability(storedProduct, request.quantity());

            double newAvailableQuantity = storedProduct.getAvailableQuantity() - request.quantity();
            storedProduct.setAvailableQuantity(newAvailableQuantity);

            productRepository.save(storedProduct);
            responses.add(productMapper.toProductPurchaseResponse(storedProduct, newAvailableQuantity));
        }

        return responses;
    }

    private void validateProductsExist(List<Integer> requestedIds, List<Product> foundProducts) {
        Set<Integer> foundIds = foundProducts.stream()
                .map(Product::getId)
                .collect(Collectors.toSet());

        List<Integer> missingIds = requestedIds.stream()
                .filter(id -> !foundIds.contains(id))
                .collect(Collectors.toList());

        if (!missingIds.isEmpty()) {
            throw new ProductPurchaseException("Products not found: " + missingIds);
        }
    }

    private void validateStockAvailability(Product product, int requestedQuantity) {
        if (product.getAvailableQuantity() < requestedQuantity) {
            throw new ProductPurchaseException(
                    String.format("Insufficient stock for product '%s'. Available: %.2f, Requested: %d",
                            product.getName(), product.getAvailableQuantity(), requestedQuantity));
        }
    }


    @Override
    public ProductResponse findProductById(Integer productId) {
        return  productRepository.findById(productId)
                .map( productMapper::toProductResponse )
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found with id " + productId));
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toProductResponse)
                .collect( Collectors.toList());
    }


}
