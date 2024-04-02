package com.kuku9.goods.domain.seller.service;

import com.kuku9.goods.domain.seller.dto.request.ProductRegistRequest;
import com.kuku9.goods.domain.seller.dto.request.ProductUpdateRequest;
import com.kuku9.goods.domain.seller.dto.response.SellProductStatisticsResponse;
import com.kuku9.goods.domain.seller.dto.response.SellingProductResponse;
import com.kuku9.goods.domain.user.entity.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface SellerService {

    // todo :: Parameter 주석 설명 추가하기
    Long createProduct(ProductRegistRequest requestDto, User user);

    Long orderProductStatus(Long productsId, User user);

    Long updateProduct(
        Long productId, ProductUpdateRequest requestDto, User user);

    List<SellingProductResponse> getSellingProduct(
        User user, LocalDate startDate, LocalDate endDate);

    SellProductStatisticsResponse getSellProductStatistics(User user);

}
