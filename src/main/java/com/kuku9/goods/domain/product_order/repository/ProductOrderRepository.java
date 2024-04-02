package com.kuku9.goods.domain.product_order.repository;

import com.kuku9.goods.domain.product_order.entity.*;
import org.springframework.data.jpa.repository.*;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {

}
