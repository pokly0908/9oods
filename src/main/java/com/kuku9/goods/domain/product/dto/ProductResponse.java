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
    private int productPrice;
    private String domainName;
    private String brandName;

    public ProductResponse(Product product) {
        this.productId = product.getId();
        this.productName = product.getName();
        this.productDescription = product.getDescription();
        this.productPrice = product.getPrice();
        this.domainName = product.getSeller().getDomainName();
        this.brandName = product.getSeller().getBrandName();
    }
}

