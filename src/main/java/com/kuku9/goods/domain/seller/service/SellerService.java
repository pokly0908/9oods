package com.kuku9.goods.domain.seller.service;

import com.kuku9.goods.domain.seller.dto.ProductRegistRequestDto;

public interface SellerService {

    void createProduct(ProductRegistRequestDto requestDto);
}
