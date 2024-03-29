package com.kuku9.goods.domain.product.controller;

import com.kuku9.goods.domain.product.dto.ProductResponse;
import com.kuku9.goods.domain.product.entity.Product;
import com.kuku9.goods.domain.product.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Long productId){
        return ResponseEntity.status(200).body(productService.getProduct(productId));
    }
    @GetMapping
    public ResponseEntity<List<Product>> getAllProduct(@RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(200).body(productService.getAllProduct(pageable).getContent());
    }
    @GetMapping("/{sellerId}")
    public ResponseEntity<List<Product>> getSellerProduct(@PathVariable Long sellerId, @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size){
        Pageable pageable = PageRequest.of(page, size);
        return productService.getSellerProduct(sellerId, pageable);
    }

}
