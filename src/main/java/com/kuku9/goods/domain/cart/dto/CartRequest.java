package com.kuku9.goods.domain.cart.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartRequest {

    private Long productId;
    private int quantity;

    public CartRequest(Long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

}
