package com.kuku9.goods.domain.product.repository;

import com.kuku9.goods.domain.product.entity.Product;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {

	Product findByIdAndSellerId(Long productId, Long id);

	Page<Product> findBySellerId(Long sellerId, Pageable pageable);

	List<Product> findBySellerId(Long sellerId);
}
