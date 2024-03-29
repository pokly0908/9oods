package com.kuku9.goods.domain.product.repository;

import com.kuku9.goods.domain.product.entity.Product;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ProductRepository {

  void save(Product product);

  Optional<Product> findById(Long productId);

  Page<Product> findAll(Pageable pageable);

  Page<Product> findBySellerId(Long sellerId, Pageable pageable);
}