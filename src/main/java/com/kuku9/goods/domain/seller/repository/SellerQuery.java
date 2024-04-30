package com.kuku9.goods.domain.seller.repository;

import com.kuku9.goods.domain.search.dto.ProductSearchResponse;
import com.kuku9.goods.domain.search.dto.SellerSearchResponse;
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
     * @param seller    상품 조회를 위한 셀러
     * @param pageable  조회 시 페이지로 나눠서 조회
     * @param startDate 조회 시작 날짜
     * @param endDate   조회 마지막 날짜
     * @return 판매된 상품의 브랜드, 이름, 갯수, 가격이 조회
     */
    Page<SoldProductResponse> getSoldProduct(
        Seller seller, Pageable pageable, LocalDate startDate, LocalDate endDate);

    /**
     * @param seller    상품 조회를 위한 셀러
     * @param startDate 조회 시작 날짜
     * @param endDate   조회 마지막 날짜
     * @return // 판매된 상품 총 판매액 조회
     */
    SoldProductSumPriceResponse getSoldProductSumPrice(
        Seller seller, LocalDate startDate, LocalDate endDate);

    /**
     * @param seller    상품 조회를 위한 셀러
     * @param startDate 조회 시작 날짜
     * @param endDate   조회 마지막 날짜
     * @return 판매된 상품 상위 10개 조회
     */
    List<SoldProductQuantityResponse> getSoldProductQuantityTopTen(
        Seller seller, LocalDate startDate, LocalDate endDate);

    /**
     * @param keyword 상품 검색 키워드
     * @return 검색 키워드와 일치하는 데이터 전체 조회
     */
    List<ProductSearchResponse> searchProduct(String keyword);

    /**
     * @param keyword 브랜드 관련 키워드
     * @return 검색 키워드와 일치하는 데이터 전체 조회
     */
    List<SellerSearchResponse> searchBrand(String keyword);

}
