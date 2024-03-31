package com.kuku9.goods.domain.product.repository;

import com.kuku9.goods.domain.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;

    @Override
    public void save(Product product) {
        productJpaRepository.save(product);
    }

    @Override
    public Product findById(Long productId) {
        return productJpaRepository.findById(productId).orElseThrow(() ->
            new IllegalArgumentException("해당 상품은 존재하지 않습니다."));

    }

    @Override
    public Product findByIdAndSellerId(Long productId, Long id) {
        return productJpaRepository.findByIdAndSellerId(productId, id);
    }
}
