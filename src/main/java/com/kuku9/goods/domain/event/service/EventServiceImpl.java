package com.kuku9.goods.domain.event.service;

import com.kuku9.goods.domain.event.dto.EventRequest;
import com.kuku9.goods.domain.event.dto.EventResponse;
import com.kuku9.goods.domain.event.dto.EventTitleResponse;
import com.kuku9.goods.domain.event.entity.Event;
import com.kuku9.goods.domain.event.repository.EventQuery;
import com.kuku9.goods.domain.event.repository.EventRepository;
import com.kuku9.goods.domain.user.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

	private final EventRepository eventRepository;
	private final EventQuery eventQuery;

	//todo: seller 권한이 있는 유저만 이벤트 등록하게 하기
	@Transactional
	public Long createEvent(EventRequest eventRequest) {

		Event event = new Event(eventRequest.getTitle(), eventRequest.getContent());
		Event savedEvent = eventRepository.save(event);

		return savedEvent.getId();
	}

	@Transactional
	public Long updateEvent(Long eventId, EventRequest request, User user) {

		Event event = findEvent(eventId);
		event.update(request.getTitle(), request.getContent());

		//todo: 생성자가 수정할 수 있도록 검증 처리 추가하기

		return event.getId();
	}

	@Transactional(readOnly = true)
	public EventResponse getEvent(Long eventId) {
		return eventQuery.getEvent(eventId);
	}

	@Transactional(readOnly = true)
	public List<EventTitleResponse> getEventTitles() {

		return eventQuery.getEventTitles();
	}

	@Transactional
	public void deleteEvent(Long eventId, User user) {

		Event event = findEvent(eventId);

		//todo: 생성자가 삭제할 수 있도록 검증 처리 추가하기

		eventRepository.delete(event);

	}

	private Event findEvent(Long eventId) {
		return eventRepository.findById(eventId)
			.orElseThrow(() -> new IllegalArgumentException("해당 이벤트는 존재하지 않습니다."));
	}
}
