package com.kuku9.goods.domain.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductInfo {

	private String name;
	private String description;
	private Long price;

}
