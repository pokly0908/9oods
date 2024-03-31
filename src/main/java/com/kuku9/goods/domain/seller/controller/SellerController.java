package com.kuku9.goods.domain.seller.controller;

import com.kuku9.goods.domain.seller.dto.ProductRegistRequestDto;
import com.kuku9.goods.domain.seller.dto.ProductUpdateRequestDto;
import com.kuku9.goods.domain.seller.service.SellerService;
import com.kuku9.goods.global.security.CustomUserDetails;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> createProduct(
        @RequestBody ProductRegistRequestDto requestDto,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long sellerId = sellerService.createProduct(requestDto, userDetails);

        return ResponseEntity.created(URI.create("/api/v1/products/seller/" + sellerId)).build();
    }

    @PatchMapping("/products/{productsId}/status")
    public ResponseEntity<Void> orderProductStatus(
        @PathVariable Long productsId,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long sellerId = sellerService.orderProductStatus(productsId, userDetails);

        return ResponseEntity.created(URI.create("/api/v1/products/seller/" + sellerId)).build();
    }

    @PatchMapping("/products/{productId}")
    public ResponseEntity<Void> updateProduct(
        @PathVariable Long productId,
        @RequestBody ProductUpdateRequestDto requestDto,
        @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long sellerId = sellerService.updateProduct(productId, requestDto, userDetails);

        return ResponseEntity.created(URI.create("/api/v1/products/seller/" + sellerId)).build();
    }

}
