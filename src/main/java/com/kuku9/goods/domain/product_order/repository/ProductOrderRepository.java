package com.kuku9.goods.domain.product_order.repository;

import com.kuku9.goods.domain.product_order.entity.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long>{

}
