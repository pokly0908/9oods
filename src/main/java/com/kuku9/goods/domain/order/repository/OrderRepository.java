package com.kuku9.goods.domain.order.repository;

import com.kuku9.goods.domain.order.entity.*;
import org.springframework.data.jpa.repository.*;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
