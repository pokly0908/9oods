package com.kuku9.goods.domain.event_product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@Getter
@AllArgsConstructor
@Value
public class EventProductRequest {

    Long productId;
}
