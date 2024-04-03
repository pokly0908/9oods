package com.kuku9.goods.domain.seller.repository;

import com.kuku9.goods.domain.seller.dto.response.SoldProductQuantityResponse;
import com.kuku9.goods.domain.seller.dto.response.SoldProductResponse;
import com.kuku9.goods.domain.seller.dto.response.SoldProductSumPriceResponse;
import com.kuku9.goods.domain.seller.entity.Seller;
import java.time.LocalDate;
import java.util.List;

public interface SellerQuery {

    List<SoldProductResponse> getSoldProduct(
        Seller seller, LocalDate startDate, LocalDate endDate);

    SoldProductSumPriceResponse getSoldProductSumPrice(
        Seller seller, LocalDate startDate, LocalDate endDate);

    List<SoldProductQuantityResponse> getSoldProductQuantityTopTen(
        Seller seller, LocalDate startDate, LocalDate endDate);
}
