package com.kuku9.goods.domain.coupon.dto;

import com.kuku9.goods.domain.coupon.entity.Coupon;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CouponResponse {

    private Long couponId;
    private LocalDate expirationDate;
    private int quantity;

    public static CouponResponse from(Coupon coupon) {
        return new CouponResponse(
            coupon.getId(),
            coupon.getExpirationDate(),
            coupon.getQuantity()
        );
    }
}
