package com.kuku9.goods.domain.seller.dto.response;

import lombok.Getter;

@Getter
public class SellingProductResponse {

    private String productName;
    private Long productPrice;
    private int productQuantity;
    private Long productTotalPrice;

    public SellingProductResponse(
        String name, Long price, int quantity, Long productTotalPrice) {
        this.productName = name;
        this.productPrice = price;
        this.productQuantity = quantity;
        this.productTotalPrice = productTotalPrice;}
}
