package com.kuku9.goods.domain.search.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SearchResponse {

    private String brandName;
    private String brandInfo;
    private String productName;
    private String productInfo;
    private int price;
    private int quantity;

}
