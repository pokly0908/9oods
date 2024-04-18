package com.kuku9.goods.domain.coupon.repository;

import com.kuku9.goods.domain.coupon.entity.Coupon;
import com.kuku9.goods.domain.coupon.entity.QCoupon;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CouponQueryImpl implements CouponQuery {

    private final JPAQueryFactory jpaQueryFactory;

    public List<Coupon> findByCategory(String su) {
        QCoupon qCoupon = QCoupon.coupon;

        return jpaQueryFactory.selectFrom(qCoupon)
            .where(qCoupon.category.eq(su))
            .fetch();
    }
}
