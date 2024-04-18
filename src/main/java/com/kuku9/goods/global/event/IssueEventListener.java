package com.kuku9.goods.global.event;

import com.kuku9.goods.domain.issued_coupon.service.IssueCouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class IssueEventListener {

    private final IssueCouponService issueCouponService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void issueCoupon(IssueEvent event) throws InterruptedException {
        Thread.sleep(1000);
        issueCouponService.issueCoupon(event.getCoupon(), event.getUser());
    }

}
