package com.kuku9.goods.domain.seller.dto;

import lombok.*;

@Getter
public class SellProductStatisticsResponseDto {

	private final Long statisticsPrice;

	public SellProductStatisticsResponseDto(long productTotalPrice) {
		this.statisticsPrice = productTotalPrice;
	}
}
