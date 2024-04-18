package com.kuku9.goods.domain.issued_coupon.service;

import com.kuku9.goods.domain.coupon.entity.Coupon;
import com.kuku9.goods.domain.user.entity.User;

public interface IssueCouponService {

    /**
     * 쿠폰 발급
     *
     * @param coupon 쿠폰
     * @param user   유저
     */

    void issueCoupon(Coupon coupon, User user);

}
