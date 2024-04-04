package com.kuku9.goods.domain.seller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductRegistRequest {
    // todo :: 나중에 제한 걸어두기

    private String productName;
    private String productDescription;
    private int productPrice;
    private Long sellerId;
    private int productQuantity;

}
