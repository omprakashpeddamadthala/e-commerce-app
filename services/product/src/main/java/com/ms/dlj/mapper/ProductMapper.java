package com.ms.dlj.mapper;

import com.ms.dlj.model.Category;
import com.ms.dlj.model.Product;
import com.ms.dlj.records.ProductPurchaseResponse;
import com.ms.dlj.records.ProductRequest;
import com.ms.dlj.records.ProductResponse;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toProduct(ProductRequest request) {
        return Product.builder()
                .id( request.id() )
                .name( request.name() )
                .description( request.description() )
                .availableQuantity( request.availableQuantity() )
                .price( request.price() )
                .category( Category.builder()
                        .id( request.categoryId() )
                        .build() )
                .build();
    }

    public ProductResponse toProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getAvailableQuantity(),
                product.getPrice(),
                product.getCategory().getId(),
                product.getCategory().getName(),
                product.getCategory().getDescription()
        );
    }

    public ProductPurchaseResponse toProductPurchaseResponse(Product storedProduct, Double quantity) {
        return new ProductPurchaseResponse(
                storedProduct.getId(),
                storedProduct.getName(),
                storedProduct.getDescription(),
                storedProduct.getPrice(),
                quantity

        );
    }
}
