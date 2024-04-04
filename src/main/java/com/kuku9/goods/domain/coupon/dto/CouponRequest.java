package com.kuku9.goods.domain.coupon.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CouponRequest {

	@NotNull(message = "쿠폰 만료일을 등록해주세요.")
	private LocalDateTime expirationDate;

	@NotNull(message = "쿠폰 수량을 등록해주세요.")
	private int quantity;
}
