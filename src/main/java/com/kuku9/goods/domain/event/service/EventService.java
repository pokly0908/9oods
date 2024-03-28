package com.kuku9.goods.domain.event.service;

import com.kuku9.goods.domain.event.dto.EventResponse;
import com.kuku9.goods.domain.event.dto.EventTitleResponse;
import com.kuku9.goods.domain.event.entity.Event;
import com.kuku9.goods.domain.event.repository.EventRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EventService {

	private final EventRepository eventRepository;

	@Transactional
	public Long createEvent(String title, String content, Long fileId) {

		Event savedEvent = eventRepository.save(new Event(title, content, fileId));

		return savedEvent.getId();
	}

	@Transactional
	public Long updateEvent(Long eventId, String title, String content, Long fileId) {

		Event event = eventRepository.findById(eventId);
		event.update(title, content, fileId);

		return eventId;
	}

	public EventResponse getEvent(Long eventId) {
		return eventRepository.getEvent(eventId);
	}

	public List<EventTitleResponse> getEventTitles() {

		return eventRepository.getEventTitles();
	}
}
