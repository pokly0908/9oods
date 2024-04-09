package com.kuku9.goods.domain.issued_coupon.repository;

public interface IssuedCouponQuery {

    void deleteExpiredCoupon();

    void deleteCoupon(Long issuedCouponId);

}
