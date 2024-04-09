package com.kuku9.goods.global.event;

import com.kuku9.goods.domain.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventListener {

	private final CouponService couponService;

	@Async
	@EventListener
	public void useCoupon(OrderEvent orderEvent) throws InterruptedException {
		Thread.sleep(1000);
		couponService.useCoupon(orderEvent.getIssuedCouponId());
	}

}
