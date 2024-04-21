package com.kuku9.goods.domain.seller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@Getter
@AllArgsConstructor
@Value
public class ProductQuantityRequest {
    // todo :: 나중에 제한 걸어두기

    int quantity;

}
