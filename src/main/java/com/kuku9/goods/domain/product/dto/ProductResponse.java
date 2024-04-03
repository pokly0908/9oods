package com.kuku9.goods.domain.product.dto;

import com.kuku9.goods.domain.product.entity.Product;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProductResponse {

    private Long productId;
    private String productName;
    private String productDescription;
    private Long productPrice;
    private String brandName;

    public ProductResponse(Product product) {
        this.productId = product.getId();
        this.productName = product.getName();
        this.productDescription = product.getDescription();
        this.productPrice = product.getPrice();
        this.brandName = product.getSeller().getBrandName();
    }
}

