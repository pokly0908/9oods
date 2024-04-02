package com.kuku9.goods.domain.order_product.repository;

import com.kuku9.goods.domain.order_product.entity.OrderProduct;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

    OrderProduct findByProductId(Long id);

    List<OrderProduct> findAllByProductOrderId(Long orderId);

}
