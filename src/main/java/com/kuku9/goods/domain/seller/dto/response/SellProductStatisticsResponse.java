package com.kuku9.goods.domain.seller.dto.response;

import lombok.*;

@Getter
public class SellProductStatisticsResponse {

    private final Long statisticsPrice;

    public SellProductStatisticsResponse(long productTotalPrice) {
        this.statisticsPrice = productTotalPrice;
    }
}
