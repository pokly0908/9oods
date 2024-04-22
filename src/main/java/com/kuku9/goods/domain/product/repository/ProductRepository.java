package com.kuku9.goods.domain.product.repository;

import com.kuku9.goods.domain.product.entity.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepository {

    Product save(Product product);

    Optional<Product> findByIdAndSellerId(Long productId, Long id);

    Optional<Product> findById(Long productId);

    Page<Product> findAll(Pageable pageable);

    List<Product> findBySellerId(Long sellerId);

    Page<Product> findBySellerId_DomainName(String domainName, Pageable pageable);

    List<Product> findAllById(List<Long> productIds);

    void deleteById(Long id);
}
