package com.kuku9.goods.domain.order_product.dto;

import lombok.Getter;
import lombok.Value;


@Getter
@Value
public class OrderProductRequest {

    Long productId;
    int quantity;

    public OrderProductRequest(Long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}
