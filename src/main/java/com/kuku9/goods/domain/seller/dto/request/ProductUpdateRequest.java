package com.kuku9.goods.domain.seller.dto.request;

import lombok.Getter;

@Getter
public class ProductUpdateRequest {
    // todo :: 나중에 제한 걸어두기

    private String name;
    private String description;
    private String price;
}
