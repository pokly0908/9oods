package com.kuku9.goods.domain.cart.dto;

import com.kuku9.goods.domain.cart.entity.Cart;
import com.kuku9.goods.domain.product.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartResponse {

    private Long id;
    private Product product;
    private Integer quantity;


    public CartResponse(Cart cart) {
        this.id = cart.getId();
        this.product = cart.getProduct();
        this.quantity = cart.getQuantity();
    }
}
