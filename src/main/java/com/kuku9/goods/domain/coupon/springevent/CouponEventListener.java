package com.kuku9.goods.domain.coupon.springevent;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CouponEventListener {

	@Async
	@EventListener
	public void sendPush(CouponEvent couponEvent) throws InterruptedException {
		Thread.sleep(2000);
		log.info(String.format("푸시 알림 발송 [쿠폰 ID: %s | 유저 ID: %s]", couponEvent.getCouponId(),
			couponEvent.getUserId()));
	}
}
