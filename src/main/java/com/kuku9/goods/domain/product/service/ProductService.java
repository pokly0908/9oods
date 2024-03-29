package com.kuku9.goods.domain.product.service;

import com.kuku9.goods.domain.product.entity.Product;
import com.kuku9.goods.domain.product.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    public Product getProduct(Long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new IllegalAccessError("찾지 못했습니다."));
    }

    public Page<Product> getAllProduct(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public ResponseEntity<List<Product>> getSellerProduct(Long sellerId, Pageable pageable) {
        return ResponseEntity.status(200).body(productRepository.findBySellerId(sellerId, pageable).getContent());
    }
}
