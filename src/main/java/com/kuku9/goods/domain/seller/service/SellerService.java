package com.kuku9.goods.domain.seller.service;

import com.kuku9.goods.domain.seller.dto.ProductRegistRequestDto;
import com.kuku9.goods.domain.seller.dto.ProductUpdateRequestDto;
import com.kuku9.goods.domain.seller.dto.SellProductStatisticsResponseDto;
import com.kuku9.goods.domain.seller.dto.SellingProductResponseDto;
import com.kuku9.goods.domain.user.entity.User;
import com.kuku9.goods.global.security.CustomUserDetails;
import java.util.List;

public interface SellerService {

    Long createProduct(ProductRegistRequestDto requestDto, User user);

    Long orderProductStatus(Long productsId, User user);

    Long updateProduct(
        Long productId, ProductUpdateRequestDto requestDto, User user);

    List<SellingProductResponseDto> getSellingProduct(User user);

    SellProductStatisticsResponseDto getSellProductStatistics(User user);

}
