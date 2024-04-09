package com.kuku9.goods.global.event;

import com.kuku9.goods.domain.issued_coupon.entity.IssuedCoupon;
import com.kuku9.goods.domain.issued_coupon.service.IssueCouponService;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class IssueEventListener {

	private final IssueCouponService issueCouponService;

	@Async
	@EventListener
	public void issueCoupon(IssueEvent event) throws InterruptedException {
		Thread.sleep(1000);
		issueCouponService.issueCoupon(event.getCoupon(), event.getUser());
	}

}
