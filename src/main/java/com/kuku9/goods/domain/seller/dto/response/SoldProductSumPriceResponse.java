package com.kuku9.goods.domain.seller.dto.response;

import lombok.Getter;

@Getter
public class SoldProductSumPriceResponse {

    private final String brandName;
    private final int totalPrice;

    public SoldProductSumPriceResponse(String brandName, int totalPrice) {
        this.brandName = brandName;
        this.totalPrice = totalPrice;
    }
}
