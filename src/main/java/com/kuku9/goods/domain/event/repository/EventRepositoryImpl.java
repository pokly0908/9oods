package com.kuku9.goods.domain.event.repository;

import com.kuku9.goods.domain.event.entity.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EventRepositoryImpl implements EventRepository {

	private final EventJpaRepository eventJpaRepository;

	public Event save(Event event) {
		return eventJpaRepository.save(event);
	}

	public Event findById(Long eventId) {
		return eventJpaRepository.findById(eventId)
			.orElseThrow(() -> new IllegalArgumentException("해당 이벤트는 존재하지 않습니다."));
	}

}
