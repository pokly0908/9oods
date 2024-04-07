package com.kuku9.goods.domain.event.repository;

import com.kuku9.goods.domain.coupon.entity.QCoupon;
import com.kuku9.goods.domain.event.entity.QEvent;
import com.kuku9.goods.domain.event_product.entity.EventProduct;
import com.kuku9.goods.domain.event_product.entity.QEventProduct;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EventQueryImpl implements EventQuery {

    private final JPAQueryFactory jpaQueryFactory;

    public List<EventProduct> getEventProducts(List<Long> eventIds) {
        return jpaQueryFactory.select(
                QEventProduct.eventProduct)
            .from(QEventProduct.eventProduct)
            .where(QEventProduct.eventProduct.event.id.in(eventIds))
            .fetch();
    }

    public void deleteEventProduct(Long eventId) {
        jpaQueryFactory.delete(QEventProduct.eventProduct)
            .where(QEventProduct.eventProduct.event.id.eq(eventId))
            .execute();
    }

    public LocalDateTime getOpenDate(Long couponId) {
        QEvent qevent = QEvent.event;

        return jpaQueryFactory.select(qevent.openAt)
            .from(qevent)
            .where(qevent.coupon.id.eq(couponId))
            .fetchOne();
    }

}
