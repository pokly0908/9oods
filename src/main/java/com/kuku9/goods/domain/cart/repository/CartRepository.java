package com.kuku9.goods.domain.cart.repository;

import com.kuku9.goods.domain.cart.entity.Cart;
import com.kuku9.goods.domain.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long>{

    List<Cart> findAllByUserAndCartStatus(User user, String 장바구니);
}
