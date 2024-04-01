package com.kuku9.goods.domain.seller.dto;

import lombok.Getter;

@Getter
public class SellingProductResponseDto {

    private String productName;
    private Long productPrice;
    private int productQuantity;
    private Long productTotalPrice;
    private Long totalPrice;

    public SellingProductResponseDto(
        String name, Long price, int quantity, Long productTotalPrice, Long totalPrice) {
        this.productName = name;
        this.productPrice = price;
        this.productQuantity = quantity;
        this.productTotalPrice = productTotalPrice;
        this.totalPrice = totalPrice;
    }
}
