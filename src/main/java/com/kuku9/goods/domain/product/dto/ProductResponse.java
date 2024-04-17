package com.kuku9.goods.domain.product.dto;

import com.kuku9.goods.domain.product.entity.Product;
import lombok.Getter;
import lombok.Value;

@Getter
@Value
public class ProductResponse {

    Long productId;
    String productName;
    String productDescription;
    int productPrice;
    String brandName;

    public ProductResponse(Product product) {
        this.productId = product.getId();
        this.productName = product.getName();
        this.productDescription = product.getDescription();
        this.productPrice = product.getPrice();
        this.brandName = product.getSeller().getBrandName();
    }
}

