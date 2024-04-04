package com.kuku9.goods.domain.issued_coupon.repository;

import com.kuku9.goods.domain.coupon.entity.Coupon;

public interface IssuedCouponQuery {

	void deleteExpiredCoupon();

}
