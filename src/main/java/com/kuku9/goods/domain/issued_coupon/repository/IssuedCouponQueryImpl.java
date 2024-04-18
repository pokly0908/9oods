package com.kuku9.goods.domain.issued_coupon.repository;

import com.kuku9.goods.domain.issued_coupon.entity.QIssuedCoupon;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class IssuedCouponQueryImpl implements IssuedCouponQuery {

    private final JPAQueryFactory jpaQueryFactory;

    @Transactional
    public void deleteExpiredCoupon() {
        QIssuedCoupon qIssuedCoupon = QIssuedCoupon.issuedCoupon;
        LocalDate now = LocalDate.now();

        jpaQueryFactory
            .update(qIssuedCoupon)
            .set(qIssuedCoupon.deletedAt, now)
            .where(qIssuedCoupon.coupon.expirationDate.before(now))
            .execute();
    }

    public void deleteCoupon(Long issuedCouponId) {
        QIssuedCoupon qIssuedCoupon = QIssuedCoupon.issuedCoupon;
        LocalDate now = LocalDate.now();

        jpaQueryFactory
            .update(qIssuedCoupon)
            .set(qIssuedCoupon.deletedAt, now)
            .where(qIssuedCoupon.id.eq(issuedCouponId))
            .execute();
    }

}
