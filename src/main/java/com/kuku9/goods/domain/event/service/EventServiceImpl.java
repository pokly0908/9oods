package com.kuku9.goods.domain.event.service;

import static com.kuku9.goods.global.exception.ExceptionStatus.INVALID_ADMIN_EVENT;
import static com.kuku9.goods.global.exception.ExceptionStatus.NOT_FOUND_EVENT;

import com.kuku9.goods.domain.event.dto.EventDto;
import com.kuku9.goods.domain.event.dto.EventRequest;
import com.kuku9.goods.domain.event.dto.EventResponse;
import com.kuku9.goods.domain.event.dto.EventTitleResponse;
import com.kuku9.goods.domain.event.dto.EventUpdateRequest;
import com.kuku9.goods.domain.event.dto.ProductInfo;
import com.kuku9.goods.domain.event.entity.Event;
import com.kuku9.goods.domain.event.repository.EventQuery;
import com.kuku9.goods.domain.event.repository.EventRepository;
import com.kuku9.goods.domain.event_product.dto.EventProductRequest;
import com.kuku9.goods.domain.event_product.entity.EventProduct;
import com.kuku9.goods.domain.event_product.repository.EventProductRepository;
import com.kuku9.goods.domain.product.entity.Product;
import com.kuku9.goods.domain.product.repository.ProductRepository;
import com.kuku9.goods.domain.seller.entity.Seller;
import com.kuku9.goods.domain.seller.repository.SellerRepository;
import com.kuku9.goods.domain.user.entity.User;
import com.kuku9.goods.global.exception.EventNotFoundException;
import com.kuku9.goods.global.exception.InvalidAdminEventException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

	private final EventRepository eventRepository;
	private final SellerRepository sellerRepository;
	private final EventQuery eventQuery;
	private final EventProductRepository eventProductRepository;
	private final ProductRepository productRepository;

	@Transactional
	public Long createEvent(EventRequest eventRequest, User user) {

		Seller seller = sellerRepository.findByUserId(user.getId());

		if (seller == null) {
			throw new InvalidAdminEventException(INVALID_ADMIN_EVENT);
		}

		Event event = new Event(eventRequest, seller);
		Event savedEvent = eventRepository.save(event);

		eventRequest.getEventProducts().stream()
			.map(EventProductRequest::getProductId)
			.map(productId -> productRepository.findById(productId)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다.")))
			.map(product -> new EventProduct(savedEvent, product))
			.forEach(eventProductRepository::save);

		return savedEvent.getId();
	}

	@Transactional
	public Long updateEvent(Long eventId, EventUpdateRequest eventRequest, User user) {

		Event event = findEvent(eventId);

		if (sellerRepository.findByUserId(user.getId()) == null) {
			throw new InvalidAdminEventException(INVALID_ADMIN_EVENT);
		}

		event.update(eventRequest);

		return event.getId();
	}

	@Transactional(readOnly = true)
	public EventResponse getEvent(Long eventId) {

		Event event = findEvent(eventId);
		List<Long> eventProducts = eventQuery.getEventProductInfo(event.getId());
		return EventResponse.from(event, eventProducts);
	}

	@Transactional(readOnly = true)
	public Page<EventDto> getAllEvents(Pageable pageable) {

		return eventRepository.findAll(pageable).map(EventDto::new);
	}

	@Transactional
	public void deleteEvent(Long eventId, User user) {

		Event event = findEvent(eventId);

		if (sellerRepository.findByUserId(user.getId()) == null) {
			throw new InvalidAdminEventException(INVALID_ADMIN_EVENT);
		}

		eventQuery.deleteEventProduct(eventId);
		eventRepository.delete(event);

	}

	@Transactional
	public void deleteEventProduct(Long eventProductId, User user) {

		if (sellerRepository.findByUserId(user.getId()) == null) {
			throw new InvalidAdminEventException(INVALID_ADMIN_EVENT);
		}

		EventProduct eventProduct = eventProductRepository.findById(eventProductId)
			.orElseThrow(() -> new IllegalArgumentException("해당 이벤트 상품은 존재하지 않습니다."));

		eventProductRepository.delete(eventProduct);
	}

	private Event findEvent(Long eventId) {
		return eventRepository.findById(eventId)
			.orElseThrow(() -> new EventNotFoundException(NOT_FOUND_EVENT));
	}
}
