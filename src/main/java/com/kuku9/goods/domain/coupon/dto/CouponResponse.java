package com.kuku9.goods.domain.coupon.dto;

import com.kuku9.goods.domain.coupon.entity.Coupon;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@Getter
@Value
public class CouponResponse {

    LocalDate expirationDate;
    int quantity;

    public static CouponResponse from(Coupon coupon) {
        return new CouponResponse(
            coupon.getExpirationDate(),
            coupon.getQuantity()
        );
    }
}
