package com.kuku9.goods.global.event;

import com.kuku9.goods.domain.coupon.service.CouponService;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CouponEventListener {

	private final CouponService couponService;

	@Async
	@EventListener
	public void sendPush(CouponEvent couponEvent) throws InterruptedException {
		Thread.sleep(1000);
		log.info(String.format("[쿠폰 ID: %s | 유저 ID: %s]", couponEvent.getCouponId(),
			couponEvent.getUserId()));
	}

	@Async
	@EventListener
	public void issueCoupons(SignupEvent signupEvent) throws InterruptedException {
		Thread.sleep(1000);
		couponService.issueCoupon(signupEvent.getUser());
	}
}
