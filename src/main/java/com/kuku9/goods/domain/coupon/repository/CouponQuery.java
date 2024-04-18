package com.kuku9.goods.domain.coupon.repository;

import com.kuku9.goods.domain.coupon.entity.Coupon;
import java.util.List;

public interface CouponQuery {

    List<Coupon> findByCategory(String su);
}
