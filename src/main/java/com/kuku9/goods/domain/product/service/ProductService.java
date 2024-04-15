package com.kuku9.goods.domain.product.service;

import com.kuku9.goods.domain.product.dto.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    /**
     * 상품 조회
     *
     * @param productId  상품 아이디
     * @param domainName 도메인 이름
     * @return 상품
     */
    ProductResponse getProduct(Long productId, String domainName);

    /**
     * 전체 상품 조회
     *
     * @param pageable 페이징 정보
     * @return 전체 상품
     */
    Page<ProductResponse> getAllProduct(Pageable pageable);

    /**
     * 셀러 상품 조회
     *
     * @param domainName 도메인 이름
     * @param pageable   페이징 정보
     * @return 셀러 상품
     */
    Page<ProductResponse> getSellerProduct(String domainName, Pageable pageable);

}
