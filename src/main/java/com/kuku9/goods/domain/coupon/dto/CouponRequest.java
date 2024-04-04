package com.kuku9.goods.domain.coupon.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class CouponRequest {

    @NotNull(message = "쿠폰 만료일을 등록해주세요.")
    private LocalDate expirationDate;

    @NotNull(message = "쿠폰 수량을 등록해주세요.")
    private int quantity;
}
