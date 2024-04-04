package com.kuku9.goods.domain.order_product.repository;

import com.kuku9.goods.domain.order_product.entity.OrderProduct;
import com.kuku9.goods.domain.product.entity.Product;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
    List<OrderProduct> findAllByOrderId(Long orderId);

}
