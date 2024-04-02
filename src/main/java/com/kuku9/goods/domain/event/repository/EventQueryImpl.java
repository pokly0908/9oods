package com.kuku9.goods.domain.event.repository;

import com.kuku9.goods.domain.event.dto.EventTitleResponse;
import com.kuku9.goods.domain.event.dto.ProductInfo;
import com.kuku9.goods.domain.event.entity.QEvent;
import com.kuku9.goods.domain.event_product.entity.QEventProduct;
import com.kuku9.goods.domain.product.entity.QProduct;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EventQueryImpl implements EventQuery {

    private final JPAQueryFactory jpaQueryFactory;

    public List<EventTitleResponse> getEventTitles() {
        return jpaQueryFactory.select(
                Projections.fields(EventTitleResponse.class, QEvent.event.title))
            .from(QEvent.event)
            .fetch();
    }

    public List<ProductInfo> getEventProductInfo(Long eventId) {
        return jpaQueryFactory.select(Projections.fields(ProductInfo.class,
                QProduct.product.name,
                QProduct.product.description,
                QProduct.product.price))
            .from(QEventProduct.eventProduct)
            .leftJoin(QProduct.product).fetchJoin()
            .on(QEventProduct.eventProduct.product.id.eq(QProduct.product.id))
            .where(QEventProduct.eventProduct.event.id.eq(eventId))
            .fetch();
    }

    public void deleteEventProduct(Long eventId) {
        long count = jpaQueryFactory.delete(QEventProduct.eventProduct)
            .where(QEventProduct.eventProduct.event.id.eq(eventId))
            .execute();
    }
}
