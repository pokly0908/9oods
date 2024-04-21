package com.kuku9.goods.domain.seller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@Getter
@AllArgsConstructor
@Value
public class ProductUpdateRequest {
    // todo :: 나중에 제한 걸어두기

    String name;
    String description;
    int price;
    int quantity;
}
