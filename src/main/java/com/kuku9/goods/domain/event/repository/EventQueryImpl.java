package com.kuku9.goods.domain.event.repository;

import com.kuku9.goods.domain.event_product.entity.QEventProduct;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EventQueryImpl implements EventQuery {

	private final JPAQueryFactory jpaQueryFactory;

	public List<Long> getEventProducts(Long eventId) {
		return jpaQueryFactory.select(
			QEventProduct.eventProduct.product.id)
			.from(QEventProduct.eventProduct)
			.where(QEventProduct.eventProduct.event.id.eq(eventId))
			.fetch();
	}

	public void deleteEventProduct(Long eventId) {
		long count = jpaQueryFactory.delete(QEventProduct.eventProduct)
			.where(QEventProduct.eventProduct.event.id.eq(eventId))
			.execute();
	}

}
