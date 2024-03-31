package com.kuku9.goods.domain.product.service;

import com.kuku9.goods.domain.product.dto.ProductResponse;
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

    public ProductResponse getProduct(Long productId) {
        return new ProductResponse(productRepository.findById(productId).orElseThrow());
    }

    public Page<ProductResponse> getAllProduct(Pageable pageable) {
        return productRepository.findAll(pageable).map(ProductResponse::new);
    }

    public Page<ProductResponse> getSellerProduct(Long sellerId, Pageable pageable) {
        return productRepository.findBySellerId(sellerId, pageable).map(ProductResponse::new);
    }
}
