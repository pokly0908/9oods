package com.kuku9.goods.domain.seller.dto.request;

import lombok.Getter;

@Getter
public class ProductUpdateRequest {

    private String name;
    private String description;
    private String price;
}
