package com.kuku9.goods.global.common.scheduler;

import com.kuku9.goods.domain.issued_coupon.repository.IssuedCouponQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j(topic = "Scheduler")
@Component
@RequiredArgsConstructor
public class Scheduler {

    private final IssuedCouponQuery issuedCouponQuery;

    @Scheduled(cron = "0 0 1 * * *") // 매일 새벽 1시
    public void updateIssuedCoupon() throws InterruptedException {
        log.info("발행 쿠폰 만료 처리 실행");
        issuedCouponQuery.deleteExpiredCoupon();
    }
}
