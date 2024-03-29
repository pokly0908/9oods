package com.kuku9.goods.domain.seller.repository;

import com.kuku9.goods.domain.seller.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerJpaRepository extends JpaRepository<Seller, Long> {

}
