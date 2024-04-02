package com.kuku9.goods.domain.product.service;

import com.kuku9.goods.domain.product.dto.*;
import com.kuku9.goods.domain.product.repository.*;
import lombok.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductResponse getProduct(Long productId) {
        return new ProductResponse(productRepository.findById(productId).orElseThrow());
    }

    @Override
    public Page<ProductResponse> getAllProduct(Pageable pageable) {
        return productRepository.findAll(pageable).map(ProductResponse::new);
    }

    @Override
    public Page<ProductResponse> getSellerProduct(Long sellerId, Pageable pageable) {
        return productRepository.findBySellerId(sellerId, pageable).map(ProductResponse::new);
    }
}
