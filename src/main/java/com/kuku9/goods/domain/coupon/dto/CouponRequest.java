package com.kuku9.goods.domain.coupon.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Value;

@Getter
@Value
public class CouponRequest {

    @NotNull(message = "쿠폰 만료일을 등록해주세요.")
    LocalDate expirationDate;

    @NotNull(message = "쿠폰 수량을 등록해주세요.")
    int quantity;

    @NotNull(message = "쿠폰 카테고리를 등록해주세요. 회원가입용 쿠폰이면 su, 일반 쿠폰이면 ds 를 입력해주세요.")
    String category;

}
