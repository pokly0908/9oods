package com.kuku9.goods.domain.event.repository;

import com.kuku9.goods.domain.event.entity.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EventRepositoryImpl implements EventRepository{

	private final EventJpaRepository eventJpaRepository;

	public Event save(Event event) {
		return eventJpaRepository.save(event);
	}

}
