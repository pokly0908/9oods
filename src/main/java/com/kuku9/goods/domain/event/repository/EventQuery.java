package com.kuku9.goods.domain.event.repository;

import java.util.List;

public interface EventQuery {

	List<Long> getEventProducts(Long eventId);

	void deleteEventProduct(Long eventId);

}
