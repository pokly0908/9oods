package com.kuku9.goods.domain.seller.dto.response;

import lombok.Getter;
import lombok.Value;

@Getter
@Value
public class SoldProductQuantityResponse {

    String productName;
    int productQuantity;

    public SoldProductQuantityResponse(String productName, int productQuantity) {
        this.productName = productName;
        this.productQuantity = productQuantity;
    }
}
