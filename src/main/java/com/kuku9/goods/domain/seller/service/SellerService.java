package com.kuku9.goods.domain.seller.service;

import com.kuku9.goods.domain.seller.dto.ProductRegistRequestDto;
import com.kuku9.goods.domain.seller.dto.ProductUpdateRequestDto;
import com.kuku9.goods.domain.seller.dto.SellingProductResponseDto;
import com.kuku9.goods.global.security.CustomUserDetails;
import java.util.List;

public interface SellerService {

    Long createProduct(ProductRegistRequestDto requestDto, CustomUserDetails userDetails);

    Long orderProductStatus(Long productsId, CustomUserDetails userDetails);

    Long updateProduct(
        Long productId, ProductUpdateRequestDto requestDto, CustomUserDetails userDetails);

    List<SellingProductResponseDto> getSellingProduct(CustomUserDetails userDetails);

}
