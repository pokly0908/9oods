package com.kuku9.goods.domain.seller.dto.response;

import lombok.Getter;
import lombok.Value;

@Getter
@Value
public class SoldProductSumPriceResponse {

    String brandName;
    int totalPrice;

    public SoldProductSumPriceResponse(String brandName, int totalPrice) {
        this.brandName = brandName;
        this.totalPrice = totalPrice;
    }
}
