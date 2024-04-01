package com.kuku9.goods.domain.event.service;

import static com.kuku9.goods.global.exception.ExceptionStatus.INVALID_ADMIN_EVENT;
import static com.kuku9.goods.global.exception.ExceptionStatus.NOT_FOUND_EVENT;

import com.kuku9.goods.domain.event.dto.EventRequest;
import com.kuku9.goods.domain.event.dto.EventResponse;
import com.kuku9.goods.domain.event.dto.EventTitleResponse;
import com.kuku9.goods.domain.event.entity.Event;
import com.kuku9.goods.domain.event.repository.EventQuery;
import com.kuku9.goods.domain.event.repository.EventRepository;
import com.kuku9.goods.domain.seller.entity.Seller;
import com.kuku9.goods.domain.seller.repository.SellerRepository;
import com.kuku9.goods.domain.user.entity.User;
import com.kuku9.goods.global.exception.EventNotFoundException;
import com.kuku9.goods.global.exception.InvalidAdminEventException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

	private final EventRepository eventRepository;
	private final SellerRepository sellerRepository;
 	private final EventQuery eventQuery;

	//todo: seller 권한이 있는 유저만 이벤트 등록하게 하기
	@Transactional
	public Long createEvent(EventRequest eventRequest, User user) {

		Seller seller = sellerRepository.findByUserId(user.getId());

		if(seller == null) {
			throw new InvalidAdminEventException(INVALID_ADMIN_EVENT);
		}

		Event event = new Event(eventRequest.getTitle(), eventRequest.getContent(),
			eventRequest.getLimitNum(), eventRequest.getOpenAt());
		Event savedEvent = eventRepository.save(event);

		return savedEvent.getId();
	}

	@Transactional
	public Long updateEvent(Long eventId, EventRequest eventRequest, User user) {

		Event event = findEvent(eventId);
		event.update(eventRequest.getTitle(), eventRequest.getContent(),
			eventRequest.getLimitNum(), eventRequest.getOpenAt());

		//todo: 생성자가 수정할 수 있도록 검증 처리 추가하기

		return event.getId();
	}

	@Transactional(readOnly = true)
	public EventResponse getEvent(Long eventId) {

		Event event = findEvent(eventId);
		return EventResponse.from(event);
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
			.orElseThrow(() -> new EventNotFoundException(NOT_FOUND_EVENT));
	}
}
