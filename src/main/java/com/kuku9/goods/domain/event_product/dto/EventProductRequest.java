package com.kuku9.goods.domain.event_product.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Value;

@Getter
@Value
public class EventProductRequest {

    Long productId;

    @JsonCreator
    public EventProductRequest(@JsonProperty("productId") Long productId) {
        this.productId = productId;
    }
}
