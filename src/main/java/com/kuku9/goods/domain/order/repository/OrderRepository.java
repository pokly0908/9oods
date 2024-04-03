package com.kuku9.goods.domain.order.repository;

import com.kuku9.goods.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
