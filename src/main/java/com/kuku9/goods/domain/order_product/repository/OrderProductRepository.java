package com.kuku9.goods.domain.order_product.repository;

import com.kuku9.goods.domain.order_product.entity.*;
import com.kuku9.goods.domain.product.entity.*;
import java.time.*;
import java.util.*;
import org.springframework.data.jpa.repository.*;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

    OrderProduct findByProductId(Long id);

    List<OrderProduct> findAllByProductOrderId(Long orderId);

    List<OrderProduct> findByProductAndCreatedAtBetween(
        Product product, LocalDateTime localDateTime, LocalDateTime localDateTime1);
}
