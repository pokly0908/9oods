package com.kuku9.goods.domain.seller.dto.response;

import lombok.Getter;
import lombok.Value;

@Getter
@Value
public class SellerCheckResponse {

	Long sellerId;

	public SellerCheckResponse(Long sellerId) {
		this.sellerId = sellerId;
	}
}
