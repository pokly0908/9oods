package com.kuku9.goods.domain.product.service;

import com.kuku9.goods.domain.product.dto.*;
import org.springframework.data.domain.*;

public interface ProductService {

    /**
     * 상품 조회
     *
     * @param productId 상품 아이디
     * @return 상품
     */
    ProductResponse getProduct(Long productId);

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
     * @param sellerId 셀러 아이디
     * @param pageable 페이징 정보
     * @return 셀러 상품
     */
    Page<ProductResponse> getSellerProduct(Long sellerId, Pageable pageable);

}
