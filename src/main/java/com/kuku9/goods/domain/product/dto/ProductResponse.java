package com.kuku9.goods.domain.product.dto;

import com.kuku9.goods.domain.product.entity.*;
import lombok.*;

@Getter
@RequiredArgsConstructor
public class ProductResponse {

	private Long productId;
	private String productName;
	private String productDescription;
	private int productPrice;
	private String brandName;

	public ProductResponse(Product product) {
		this.productId = product.getId();
		this.productName = product.getName();
		this.productDescription = product.getDescription();
		this.productPrice = product.getPrice();
		this.brandName = product.getSeller().getBrandName();
	}
}

