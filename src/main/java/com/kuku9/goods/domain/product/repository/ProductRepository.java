package com.kuku9.goods.domain.product.repository;

import com.kuku9.goods.domain.product.entity.Product;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>{

  Page<Product> findBySellerId(Long sellerId, Pageable pageable);
}
