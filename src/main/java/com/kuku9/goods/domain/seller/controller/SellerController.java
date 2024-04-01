package com.kuku9.goods.domain.seller.controller;

import com.kuku9.goods.domain.seller.dto.ProductRegistRequestDto;
import com.kuku9.goods.domain.seller.dto.ProductUpdateRequestDto;
import com.kuku9.goods.domain.seller.dto.SellProductStatisticsResponseDto;
import com.kuku9.goods.domain.seller.dto.SellingProductResponseDto;
import com.kuku9.goods.domain.seller.service.SellerService;
import com.kuku9.goods.global.security.CustomUserDetails;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.aspectj.weaver.ast.Literal;
import org.hibernate.annotations.Comment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
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

    // 상품 등록 기능
    @PostMapping("/products")
    public ResponseEntity<String> createProduct(
        @RequestBody ProductRegistRequestDto requestDto,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long sellerId = sellerService.createProduct(requestDto, userDetails.getUser());

        return ResponseEntity.created(URI.create("/api/v1/products/seller/" + sellerId)).build();
    }

    // 상품 판매 여부 기능
    @PatchMapping("/products/{productsId}/status")
    public ResponseEntity<Void> orderProductStatus(
        @PathVariable Long productsId,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long sellerId = sellerService.orderProductStatus(productsId, userDetails.getUser());

        return ResponseEntity.created(URI.create("/api/v1/products/seller/" + sellerId)).build();
    }

    // 상품 정보 수정 기능
    @PatchMapping("/products/{productId}")
    public ResponseEntity<Void> updateProduct(
        @PathVariable Long productId,
        @RequestBody ProductUpdateRequestDto requestDto,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long sellerId = sellerService.updateProduct(productId, requestDto, userDetails.getUser());

        return ResponseEntity.created(URI.create("/api/v1/products/seller/" + sellerId)).build();
    }

    // 셀러의 판매된 상품 정보 조회 기능
    @GetMapping("/products/selled")
    public ResponseEntity<List<SellingProductResponseDto>> getSellingProduct (
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<SellingProductResponseDto> responseDto = sellerService.getSellingProduct(
            userDetails.getUser());

        return ResponseEntity.ok(responseDto);
    }

}
