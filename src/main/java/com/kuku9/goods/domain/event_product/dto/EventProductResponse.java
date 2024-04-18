package com.kuku9.goods.domain.event_product.dto;

import com.kuku9.goods.domain.event_product.entity.EventProduct;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EventProductResponse {

    private Long productId;

    public EventProductResponse(EventProduct eventProduct) {
        this.productId = eventProduct.getProduct().getId();
    }
}
