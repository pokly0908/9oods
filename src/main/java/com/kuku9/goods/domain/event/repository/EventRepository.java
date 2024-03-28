package com.kuku9.goods.domain.event.repository;

import com.kuku9.goods.domain.event.entity.Event;

public interface EventRepository {

	Event save(Event event);

}
