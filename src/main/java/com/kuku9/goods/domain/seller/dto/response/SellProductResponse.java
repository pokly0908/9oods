package com.kuku9.goods.domain.seller.dto.response;

import lombok.Getter;

@Getter
public class SellProductResponse {

    private final String productName;
    private final int productPrice;
    private final int productQuantity;
    private final int productTotalPrice;

    public SellProductResponse(
        String name, int price, int quantity, int productTotalPrice) {
        this.productName = name;
        this.productPrice = price;
        this.productQuantity = quantity;
        this.productTotalPrice = productTotalPrice;
    }
}
