package com.kuku9.goods.domain.seller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductRegistRequest {

	private String productName;
	private String productDescription;
	private Long productPrice;
	private Long sellerId;

}
