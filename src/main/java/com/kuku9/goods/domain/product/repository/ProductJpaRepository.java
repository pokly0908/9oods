package com.kuku9.goods.domain.product.repository;

import com.kuku9.goods.domain.product.entity.*;
import java.util.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {

    Product findByIdAndSellerId(Long productId, Long id);

    Page<Product> findBySellerId(Long sellerId, Pageable pageable);

    List<Product> findBySellerId(Long sellerId);
}
