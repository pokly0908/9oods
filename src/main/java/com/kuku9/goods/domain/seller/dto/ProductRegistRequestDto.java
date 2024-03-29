package com.kuku9.goods.domain.seller.dto;

import lombok.Getter;

@Getter
public class ProductRegistRequestDto {

  private String productName;
  private String productDescription;
  private int productPrice;
  private Long sellerId;

}
