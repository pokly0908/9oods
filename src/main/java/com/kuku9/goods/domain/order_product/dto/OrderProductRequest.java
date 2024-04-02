package com.kuku9.goods.domain.order_product.dto;

import lombok.*;

@RequiredArgsConstructor
@Getter
public class OrderProductRequest {

	private Long productId;
	private int quantity;

	public OrderProductRequest(Long productId, int quantity) {
		this.productId = productId;
		this.quantity = quantity;
	}
}
