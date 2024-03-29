package com.kuku9.goods.domain.order_product.dto;

import com.kuku9.goods.domain.order_product.entity.OrderProduct;
import com.kuku9.goods.domain.product.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderProductResponse {

    private Long id;
    private Product product;
    private Integer quantity;

}
