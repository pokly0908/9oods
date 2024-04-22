package com.kuku9.goods.domain.seller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@Getter
@AllArgsConstructor
@Value
public class ProductUpdateRequest {

    String name;
    String description;
    int price;
    int quantity;

}
