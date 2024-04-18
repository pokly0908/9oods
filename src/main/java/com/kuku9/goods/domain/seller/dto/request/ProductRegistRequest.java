package com.kuku9.goods.domain.seller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ProductRegistRequest {
    // todo :: 나중에 제한 걸어두기

    private Long sellerId;
    private String productName;
    private String productDescription;
    private int productPrice;
    private int productQuantity;

}
