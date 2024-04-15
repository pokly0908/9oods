package com.kuku9.goods.domain.issued_coupon.service;

import com.kuku9.goods.domain.coupon.entity.Coupon;
import com.kuku9.goods.domain.issued_coupon.entity.IssuedCoupon;
import com.kuku9.goods.domain.issued_coupon.repository.IssuedCouponRepository;
import com.kuku9.goods.domain.user.entity.User;
import com.kuku9.goods.global.event.CouponEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class IssueCouponServiceImpl implements IssueCouponService {

    private final IssuedCouponRepository issuedCouponRepository;
    private final ApplicationEventPublisher publisher;

    @Transactional
    public void issueCoupon(Coupon coupon, User user) {
        IssuedCoupon issuedCoupon = new IssuedCoupon(user, coupon);
        issuedCouponRepository.save(issuedCoupon);
        log.info(
            String.format("쿠폰 발급 처리 [쿠폰 ID : %s]", issuedCoupon.getCoupon().getId()));
        publisher.publishEvent(
            new CouponEvent(issuedCoupon.getCoupon().getId(),
                issuedCoupon.getUser().getId()));
    }
}
