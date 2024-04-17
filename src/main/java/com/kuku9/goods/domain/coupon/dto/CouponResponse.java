package com.kuku9.goods.domain.coupon.dto;

import com.kuku9.goods.domain.coupon.entity.Coupon;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@Getter
@Value
public class CouponResponse {

    Long couponId;
    LocalDate expirationDate;
    int quantity;
    String category;
    public static CouponResponse from(Coupon coupon) {
        return new CouponResponse(
            coupon.getId(),
            coupon.getExpirationDate(),
            coupon.getQuantity(),
            coupon.getCategory()
        );
    }
}
