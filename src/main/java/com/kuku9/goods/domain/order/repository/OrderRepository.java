package com.kuku9.goods.domain.order.repository;

import com.kuku9.goods.domain.order.entity.Order;
import com.kuku9.goods.domain.user.entity.User;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Range;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

	Page<Order> findAllByUser(User user, Pageable pageable);
}
