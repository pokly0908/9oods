package com.kuku9.goods.domain.event.service;

import com.kuku9.goods.domain.event.entity.Event;
import com.kuku9.goods.domain.event.repository.EventRepository;
import com.kuku9.goods.domain.file.entity.File;
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
}
