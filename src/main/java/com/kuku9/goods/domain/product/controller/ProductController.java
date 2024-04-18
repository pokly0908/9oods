package com.kuku9.goods.domain.product.controller;

import com.kuku9.goods.domain.product.dto.ProductResponse;
import com.kuku9.goods.domain.product.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductService productService;

    // 낱개조회
    @GetMapping("/sellers/{domainName}/products/{productId}")
    public ResponseEntity<ProductResponse> getProduct(
        @PathVariable Long productId, @PathVariable String domainName) {
        return ResponseEntity.ok().body(productService.getProduct(productId, domainName));
    }

    //전체 조회
    @GetMapping("/products")
    public ResponseEntity<List<ProductResponse>> getAllProduct(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok().body(productService.getAllProduct(pageable).getContent());
    }

    //셀러 별 조회
    @GetMapping("/sellers/{domainName}/products")
    public ResponseEntity<List<ProductResponse>> getSellerProduct(
        @PathVariable String domainName,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok()
            .body(productService.getSellerProduct(domainName, pageable).getContent());
    }

}
