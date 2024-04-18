package com.kuku9.goods.global.event;

import lombok.Getter;

@Getter
public class OrderEvent {

    private final Long issuedCouponId;

    public OrderEvent(Long issuedCouponId) {
        this.issuedCouponId = issuedCouponId;
    }

}
