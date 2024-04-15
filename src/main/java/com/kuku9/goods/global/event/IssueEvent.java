package com.kuku9.goods.global.event;

import com.kuku9.goods.domain.coupon.entity.Coupon;
import com.kuku9.goods.domain.user.entity.User;
import lombok.Getter;

@Getter
public class IssueEvent {

    private final Coupon coupon;
    private final User user;

    public IssueEvent(Coupon coupon, User user) {
        this.coupon = coupon;
        this.user = user;
    }

}
