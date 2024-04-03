package com.kuku9.goods.domain.seller.dto.response;

import lombok.Getter;

@Getter
public class SoldProductResponse {

    private final String productName;
    private final int productPrice;
    private final int productQuantity;
    private final int orderProductQuantity;
    private final int productTotalPrice;

    public SoldProductResponse(
        String name, int price, int quantity, int orderProductQuantity, int productTotalPrice) {
        this.productName = name;
        this.productPrice = price;
        this.productQuantity = quantity;
        this.orderProductQuantity = orderProductQuantity;
        this.productTotalPrice = productTotalPrice;
    }
}
