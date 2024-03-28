package com.kuku9.goods.domain.cart.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class CartsResponse {
    List<CartResponse> carts;

    public CartsResponse(List<CartResponse> carts) {
        this.carts = carts;
    }
}
