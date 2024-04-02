package com.kuku9.goods.domain.product.repository;

import com.kuku9.goods.domain.product.entity.*;
import java.util.*;
import org.springframework.data.domain.*;

public interface ProductRepository {

	void save(Product product);

	Product findByIdAndSellerId(Long productId, Long id);

	Optional<Product> findById(Long productId);

	Page<Product> findAll(Pageable pageable);

	Page<Product> findBySellerId(Long sellerId, Pageable pageable);

	List<Product> findBySellerId(Long sellerId);
}
