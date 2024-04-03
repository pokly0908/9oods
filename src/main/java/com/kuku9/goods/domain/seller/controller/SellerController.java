package com.kuku9.goods.domain.seller.controller;

import com.kuku9.goods.domain.seller.dto.request.ProductRegistRequest;
import com.kuku9.goods.domain.seller.dto.request.ProductUpdateRequest;
import com.kuku9.goods.domain.seller.dto.response.SellProductResponse;
import com.kuku9.goods.domain.seller.dto.response.SellProductStatisticsResponse;
import com.kuku9.goods.domain.seller.service.SellerService;
import com.kuku9.goods.global.security.CustomUserDetails;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sellers")
public class SellerController {

    private final SellerService sellerService;

    // 상품 등록 기능
    @PostMapping("/products")
    @PreAuthorize("hasRole('ROLE_SELLER')")
    public ResponseEntity<String> createProduct(
        @RequestBody ProductRegistRequest requestDto,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long sellerId = sellerService.createProduct(requestDto, userDetails.getUser());

        return ResponseEntity.created(URI.create("/api/v1/products/seller/" + sellerId)).build();
    }

    // 상품 판매 여부 기능
    @PatchMapping("/products/{productId}/status")
    @PreAuthorize("hasRole('ROLE_SELLER')")
    public ResponseEntity<Void> orderProductStatus(
        @PathVariable Long productId,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long sellerId = sellerService.orderProductStatus(productId, userDetails.getUser());

        return ResponseEntity.created(URI.create("/api/v1/products/seller/" + sellerId)).build();
    }

    // 상품 정보 수정 기능
    @PatchMapping("/products/{productId}")
    @PreAuthorize("hasRole('ROLE_SELLER')")
    public ResponseEntity<Void> updateProduct(
        @PathVariable Long productId,
        @RequestBody ProductUpdateRequest requestDto,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long sellerId = sellerService.updateProduct(productId, requestDto, userDetails.getUser());

        return ResponseEntity.created(URI.create("/api/v1/products/seller/" + sellerId)).build();
    }

    // 셀러의 판매된 상품 정보 원하는 날짜 선택 조회 기능
    @GetMapping("/products/sold")
    @PreAuthorize("hasRole('ROLE_SELLER')")
    public ResponseEntity<List<SellProductResponse>> getSellingProduct(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        List<SellProductResponse> responseDto = sellerService.getSellingProduct(
            userDetails.getUser(), startDate, endDate);

        return ResponseEntity.ok(responseDto);
    }

    // 셀러의 판매된 상품 통계
    @GetMapping("/products/sold/statistics")
    @PreAuthorize("hasRole('ROLE_SELLER')")
    public ResponseEntity<SellProductStatisticsResponse> getSellProductStatistics(
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        SellProductStatisticsResponse responseDto = sellerService.getSellProductStatistics(
            userDetails.getUser());

        return ResponseEntity.ok(responseDto);
    }

}
