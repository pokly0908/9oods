package com.kuku9.goods.domain.order_product.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class OrderProductsResponse {
    List<OrderProductResponse> carts;

    public OrderProductsResponse(List<OrderProductResponse> carts) {
        this.carts = carts;
    }
}
