package com.kuku9.goods.domain.issued_coupon.repository;

import com.kuku9.goods.domain.issued_coupon.entity.IssuedCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssuedCouponRepository extends JpaRepository<IssuedCoupon, Long> {

	boolean existsByUserId(Long userId);

}
