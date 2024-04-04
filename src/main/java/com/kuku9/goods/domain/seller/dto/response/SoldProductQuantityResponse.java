package com.kuku9.goods.domain.seller.dto.response;

import lombok.Getter;

@Getter
public class SoldProductQuantityResponse {

    private final String productName;
    private final int productQuantity;

    public SoldProductQuantityResponse(String productName, int productQuantity) {
        this.productName = productName;
        this.productQuantity = productQuantity;
    }
}
