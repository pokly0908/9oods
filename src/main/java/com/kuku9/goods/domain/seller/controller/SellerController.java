package com.kuku9.goods.domain.seller.controller;

import com.kuku9.goods.domain.search.dto.ProductSearchResponse;
import com.kuku9.goods.domain.search.dto.SellerSearchResponse;
import com.kuku9.goods.domain.seller.dto.response.*;
import com.kuku9.goods.domain.seller.dto.request.ProductRegistRequest;
import com.kuku9.goods.domain.seller.dto.request.ProductUpdateRequest;
import com.kuku9.goods.domain.seller.service.SellerService;
import com.kuku9.goods.global.security.CustomUserDetails;
import jakarta.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public ResponseEntity<Void> createProduct(
        @RequestBody @Valid ProductRegistRequest requestDto,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        String domainName = sellerService.createProduct(requestDto, userDetails.getUser());

        return ResponseEntity.created(
            URI.create("/api/v1/seller/" + domainName + "/products")).build();
    }

    // 상품 판매 여부 기능
    @PatchMapping("/products/{productId}/status")
    @PreAuthorize("hasRole('ROLE_SELLER')")
    public ResponseEntity<Void> updateProductStatus(
        @PathVariable Long productId,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        String domainName = sellerService.updateProductStatus(productId, userDetails.getUser());

        return ResponseEntity.created(
            URI.create("/api/v1/seller/" + domainName + "/products")).build();
    }

    // 상품 정보 수정 기능
    @PatchMapping("/products/{productId}")
    @PreAuthorize("hasRole('ROLE_SELLER')")
    public ResponseEntity<Void> updateProduct(
        @PathVariable Long productId,
        @RequestBody ProductUpdateRequest requestDto,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        String domainName =
            sellerService.updateProduct(productId, requestDto, userDetails.getUser());

        return ResponseEntity.created(
            URI.create("/api/v1/seller/" + domainName + "/products")).build();
    }


    // 상품 삭제
    @DeleteMapping("/products/{productId}")
    @PreAuthorize("hasRole('ROLE_SELLER')")
    public ResponseEntity<Void> deleteProduct(
        @PathVariable Long productId,
        @AuthenticationPrincipal CustomUserDetails userDetails) {

        String domainName = sellerService.deleteProduct(productId, userDetails.getUser());

        return ResponseEntity.created(
            URI.create("/api/v1/seller/" + domainName + "/products")).build();
    }

    // 셀러의 판매된 상품 정보 원하는 날짜 선택 조회 기능
    @GetMapping("/products/sold")
    @PreAuthorize("hasRole('ROLE_SELLER')")
    public ResponseEntity<Page<SoldProductResponse>> getSoldProduct(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @RequestParam int page,
        @RequestParam int size,
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {

        Pageable pageable = PageRequest.of(
            page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<SoldProductResponse> responseDto = sellerService.getSoldProduct(
            userDetails.getUser(), pageable, startDate, endDate);

        return ResponseEntity.ok(responseDto);
    }

    // 셀러의 판매된 상품 총 판매액 조회
    @GetMapping("/products/sold/price")
    @PreAuthorize("hasRole('ROLE_SELLER')")
    public ResponseEntity<SoldProductSumPriceResponse> getSoldProductSumPrice(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        SoldProductSumPriceResponse responseDto = sellerService.getSoldProductSumPrice(
            userDetails.getUser(), startDate, endDate);

        return ResponseEntity.ok(responseDto);
    }

    // 셀러의 판매된 상품 중 탑 10
    @GetMapping("/products/sold/topten")
    @PreAuthorize("hasRole('ROLE_SELLER')")
    public ResponseEntity<List<SoldProductQuantityResponse>> getSoldProductQuantityTopTen(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {

        List<SoldProductQuantityResponse> responses = sellerService.getSoldProductQuantityTopTen(
            userDetails.getUser(), startDate, endDate);

        return ResponseEntity.ok(responses);
    }

    // 상품 검색
    @GetMapping("/products")
    public List<ProductSearchResponse> searchProduct(
        @RequestParam String keyword) {
        return sellerService.searchProduct(keyword);
    }

    // 상품 설명 검색
    @GetMapping("/brands")
    public List<SellerSearchResponse> searchBrand(
        @RequestParam String keyword) {
        return sellerService.searchBrand(keyword);
    }

}
