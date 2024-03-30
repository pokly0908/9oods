package com.kuku9.goods.domain.seller.service;

import com.kuku9.goods.domain.seller.dto.ProductRegistRequestDto;
import com.kuku9.goods.global.security.CustomUserDetails;

public interface SellerService {

  void createProduct(ProductRegistRequestDto requestDto, CustomUserDetails userDetails);

  void orderProductStatus(Long productsId, CustomUserDetails userDetails);
}
