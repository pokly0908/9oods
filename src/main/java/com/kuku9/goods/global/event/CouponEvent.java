package com.kuku9.goods.global.event;

import lombok.Getter;

@Getter
public class CouponEvent {

    private final Long couponId;
    private final Long userId;

    public CouponEvent(Long couponId, Long userId) {
        this.couponId = couponId;
        this.userId = userId;
    }

}
