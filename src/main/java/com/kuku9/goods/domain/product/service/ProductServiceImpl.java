package com.kuku9.goods.domain.product.service;

import com.kuku9.goods.domain.product.dto.ProductResponse;
import com.kuku9.goods.domain.product.entity.Product;
import com.kuku9.goods.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    @Cacheable(value = "productCache", key = "#productId", unless = "#result == null")
    public ProductResponse getProduct(Long productId, String domainName) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
        if(!product.getSeller().getDomainName().equals(domainName)) {
            throw new IllegalArgumentException("잘못된 정보입니다.");
        }
        return new ProductResponse(product);
    }

    @Override
    public Page<ProductResponse> getAllProduct(Pageable pageable) {
        return productRepository.findAll(pageable).map(ProductResponse::new);
    }

    @Override
    public Page<ProductResponse> getSellerProduct(String domainName, Pageable pageable) {
        return productRepository.findBySellerId_DomainName(domainName, pageable).map(ProductResponse::new);
    }
}
