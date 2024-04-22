package com.kuku9.goods.domain.seller.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Builder
@Getter
@Value
@AllArgsConstructor
public class ProductRegistRequest {

    @NotBlank(message = "상품명이 입력되지 않았습니다.")
    String productName;
    @NotBlank(message = "상품 설명이 입력되지 않았습니다.")
    String productDescription;
    @NotBlank(message = "상품 가격이 입력되지 않았습니다.")
    int productPrice;
    @NotBlank(message = "상품 수량이 입력되지 않았습니다.")
    int productQuantity;

}
