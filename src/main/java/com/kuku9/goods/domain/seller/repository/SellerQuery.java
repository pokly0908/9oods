package com.kuku9.goods.domain.seller.repository;

import com.kuku9.goods.domain.seller.dto.response.SoldProductQuantityResponse;
import com.kuku9.goods.domain.seller.dto.response.SoldProductResponse;
import com.kuku9.goods.domain.seller.dto.response.SoldProductSumPriceResponse;
import com.kuku9.goods.domain.seller.entity.Seller;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SellerQuery {

    /**
     *
     * @param seller 상품 조회를 위한 셀러
     * @param pageable 조회 시 페이지로 나눠서 조회
     * @param startDate 조회 시작 날짜
     * @param endDate 조회 마지막 날짜
     * @return 판매된 상품의 브랜드, 이름, 갯수, 가격이 조회
     */
    Page<SoldProductResponse> getSoldProduct(
        Seller seller, Pageable pageable, LocalDate startDate, LocalDate endDate);

    /**
     *
     * @param seller 상품 조회를 위한 셀러
     * @param startDate 조회 시작 날짜
     * @param endDate 조회 마지막 날짜
     * @return
     */
    SoldProductSumPriceResponse getSoldProductSumPrice(
        Seller seller, LocalDate startDate, LocalDate endDate);

    /**
     * @param seller 상품 조회를 위한 셀러
     * @param startDate 조회 시작 날짜
     * @param endDate 조회 마지막 날짜
     * @return
     */
    List<SoldProductQuantityResponse> getSoldProductQuantityTopTen(
        Seller seller, LocalDate startDate, LocalDate endDate);
}
