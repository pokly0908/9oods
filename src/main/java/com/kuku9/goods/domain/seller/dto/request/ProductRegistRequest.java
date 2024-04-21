package com.kuku9.goods.domain.seller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Builder
@Getter
@Value
@AllArgsConstructor
public class ProductRegistRequest {
    // todo :: 나중에 제한 걸어두기

    Long sellerId;
    String productName;
    String productDescription;
    int productPrice;
    int productQuantity;

}
