package com.kuku9.goods.domain.event_product.dto;

import com.kuku9.goods.domain.event_product.entity.EventProduct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;

@Getter
@Value
public class EventProductResponse {

	Long productId;

	public EventProductResponse(EventProduct eventProduct) {
		this.productId = eventProduct.getProduct().getId();
	}
}
