package com.kuku9.goods.domain.order_product.dto;

import lombok.Value;

@Value
public class OrderProductResponse {

    Long productId;
    String productName;
    String productDescription;
    String productPrice;
    String brandName;
}
