package com.kuku9.goods.domain.seller.dto.request;

import lombok.Getter;
import lombok.Value;

@Getter
@Value
public class ProductQuantityRequest {
    // todo :: 나중에 제한 걸어두기

    int quantity;

}
