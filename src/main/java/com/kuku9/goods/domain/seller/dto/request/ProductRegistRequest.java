package com.kuku9.goods.domain.seller.dto.request;

import lombok.Getter;
import lombok.Value;

@Getter
@Value
public class ProductRegistRequest {
    // todo :: 나중에 제한 걸어두기

    String productName;
    String productDescription;
    int productPrice;
    Long sellerId;
    int productQuantity;

}
