package com.kuku9.goods.domain.seller.controller;

import com.kuku9.goods.domain.seller.dto.ProductRegistRequestDto;
import com.kuku9.goods.domain.seller.dto.ProductUpdateRequestDto;
import com.kuku9.goods.domain.seller.service.SellerService;
import com.kuku9.goods.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sellers")
public class SellerController {

    private final SellerService sellerService;

    @PostMapping("/products")
    public String createProduct(
        @RequestBody ProductRegistRequestDto requestDto,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        sellerService.createProduct(requestDto, userDetails);

        return "상품 생성";
    }

    @PatchMapping("/products/{productsId}/status")
    public String orderProductStatus(
        @PathVariable Long productsId,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        sellerService.orderProductStatus(productsId, userDetails);

        return "물품을 배송해야 합니다.";
    }

    @PatchMapping("/products/{productId}")
    public String updateProduct(
        @PathVariable Long productId,
        @RequestBody ProductUpdateRequestDto requestDto,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        sellerService.updateProduct(productId, requestDto, userDetails);

        return "상품 정보가 수정되었습니다.";
    }

}
