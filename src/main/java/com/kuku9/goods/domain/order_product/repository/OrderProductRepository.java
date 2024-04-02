package com.kuku9.goods.domain.order_product.repository;

import com.kuku9.goods.domain.order_product.entity.*;
import java.util.*;
import org.springframework.data.jpa.repository.*;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

	OrderProduct findByProductId(Long id);

	List<OrderProduct> findAllByProductOrderId(Long orderId);

}
