package com.kuku9.goods.domain.seller.dto;

import lombok.Getter;

@Getter
public class SellingProductResponseDto {

	private final String productName;
	private final Long productPrice;
	private final int productQuantity;
	private final Long productTotalPrice;
	private final Long totalPrice;

	public SellingProductResponseDto(
		String name, Long price, int quantity, Long productTotalPrice, Long totalPrice) {
		this.productName = name;
		this.productPrice = price;
		this.productQuantity = quantity;
		this.productTotalPrice = productTotalPrice;
		this.totalPrice = totalPrice;
	}
}
