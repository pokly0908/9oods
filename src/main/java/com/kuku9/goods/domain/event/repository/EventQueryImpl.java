package com.kuku9.goods.domain.event.repository;

import com.kuku9.goods.domain.coupon.entity.QCoupon;
import com.kuku9.goods.domain.event.dto.EventResponse;
import com.kuku9.goods.domain.event.entity.QEvent;
import com.kuku9.goods.domain.event_product.dto.EventProductResponse;
import com.kuku9.goods.domain.event_product.entity.EventProduct;
import com.kuku9.goods.domain.event_product.entity.QEventProduct;
import com.kuku9.goods.domain.product.entity.QProduct;
import com.kuku9.goods.domain.seller.entity.QSeller;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EventQueryImpl implements EventQuery {

    private final JPAQueryFactory jpaQueryFactory;

    public List<EventProductResponse> getEventProducts(List<Long> eventIds) {
        QEventProduct qEventProduct = QEventProduct.eventProduct;
        QProduct qProduct = QProduct.product;
        QSeller qSeller = QSeller.seller;

        return jpaQueryFactory.select(Projections.constructor(EventProductResponse.class,
            qEventProduct.event.id.as("eventId"),
            qProduct.name.as("productName"),
            qProduct.description.as("productDescription"),
            qProduct.price.as("productPrice"),
            qSeller.domainName
            ))
            .from(QEventProduct.eventProduct)
            .join(qProduct).on(qProduct.id.eq(qEventProduct.product.id))
            .join(qSeller).on(qProduct.seller.id.eq(qSeller.id))
            .where(
                QEventProduct.eventProduct.event.id.in(eventIds)
            )
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

    public List<EventResponse> getEvents() {
        QEvent qEvent = QEvent.event;
        QCoupon qCoupon = QCoupon.coupon;

        return jpaQueryFactory
            .select(Projections.constructor(EventResponse.class,
                qEvent.id,
                qEvent.title,
                qEvent.content,
                qEvent.openAt,
                qCoupon.id.as("couponId"),
                qCoupon.expirationDate,
                qCoupon.quantity))
            .from(qEvent)
            .join(qCoupon).on(qCoupon.id.eq(qEvent.coupon.id)).fetchJoin()
            .fetch();
    }

    public EventResponse getEvent(Long eventId) {
        QEvent qEvent = QEvent.event;
        QCoupon qCoupon = QCoupon.coupon;

        return jpaQueryFactory
            .select(Projections.constructor(EventResponse.class,
                qEvent.id,
                qEvent.title,
                qEvent.content,
                qEvent.openAt,
                qCoupon.id.as("couponId"),
                qCoupon.expirationDate,
                qCoupon.quantity))
            .from(qEvent)
            .join(qCoupon).on(qCoupon.id.eq(qEvent.coupon.id))
            .where(qEvent.id.eq(eventId))
            .fetchOne();
    }


}
