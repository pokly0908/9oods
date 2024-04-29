package com.kuku9.goods.domain.event.service;

import static com.kuku9.goods.global.exception.ExceptionStatus.INVALID_ADMIN_EVENT;
import static com.kuku9.goods.global.exception.ExceptionStatus.NOT_FOUND;

import com.kuku9.goods.domain.coupon.entity.Coupon;
import com.kuku9.goods.domain.coupon.repository.CouponRepository;
import com.kuku9.goods.domain.event.dto.AllEventResponse;
import com.kuku9.goods.domain.event.dto.EventRequest;
import com.kuku9.goods.domain.event.dto.EventResponse;
import com.kuku9.goods.domain.event.dto.EventUpdateRequest;
import com.kuku9.goods.domain.event.entity.Event;
import com.kuku9.goods.domain.event.repository.EventQuery;
import com.kuku9.goods.domain.event.repository.EventRepository;
import com.kuku9.goods.domain.event_product.dto.EventProductRequest;
import com.kuku9.goods.domain.event_product.dto.EventProductResponse;
import com.kuku9.goods.domain.event_product.entity.EventProduct;
import com.kuku9.goods.domain.event_product.repository.EventProductRepository;
import com.kuku9.goods.domain.product.repository.ProductRepository;
import com.kuku9.goods.domain.user.entity.User;
import com.kuku9.goods.global.exception.InvalidAdminEventException;
import com.kuku9.goods.global.exception.NotFoundException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventQuery eventQuery;
    private final EventProductRepository eventProductRepository;
    private final ProductRepository productRepository;
    private final CouponRepository couponRepository;

    @Transactional
    public Long createEvent(EventRequest eventRequest, User user) {

        Event event = new Event(eventRequest, user);
        Event savedEvent = eventRepository.save(event);

        if (eventRequest.getEventProducts() != null) {
            eventRequest.getEventProducts().stream()
                .map(EventProductRequest::getProductId)
                .map(productId -> productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다.")))
                .map(product -> new EventProduct(savedEvent, product))
                .forEach(eventProductRepository::save);

        }

        if (eventRequest.getCouponId() != null) {
            Coupon coupon = findCoupon(eventRequest.getCouponId());

            savedEvent.addCoupon(coupon);
        }

        return savedEvent.getId();
    }

    @Transactional
    @CacheEvict(value = "eventCache", key = "#eventId")
    public Long updateEvent(Long eventId, EventUpdateRequest eventRequest, User user) {

        Event event = findEvent(eventId);

        if (!event.getUser().getId().equals(user.getId())) {
            throw new InvalidAdminEventException(INVALID_ADMIN_EVENT);
        }

        event.update(eventRequest);

        return event.getId();
    }


    @Cacheable(value = "eventCache", key = "#eventId", unless = "#result == null")
    public AllEventResponse getEvent(Long eventId) {

        EventResponse eventResponse = eventQuery.getEvent(eventId);
        List<EventProductResponse> eventProductResponses = eventQuery.getEventProducts(
            List.of(eventResponse.getId()));

        return AllEventResponse.from(eventResponse, eventProductResponses);
    }

    @Transactional(readOnly = true)
    public Page<AllEventResponse> getAllEvents(Pageable pageable) {
        List<EventResponse> events = eventQuery.getEvents();
        List<Long> eventIds = events.stream().map(EventResponse::getId).toList();
        List<EventProductResponse> eventProducts = eventQuery.getEventProducts(eventIds);

        Map<Long, List<EventProductResponse>> eventProductMap = eventProducts.stream()
            .collect(Collectors.groupingBy(EventProductResponse::getEventId));

        List<AllEventResponse> allEventResponses = events.stream()
            .map(event -> AllEventResponse.from(event,
                eventProductMap.getOrDefault(event.getId(), List.of()))).toList();
        return new PageImpl<>(allEventResponses, pageable, events.size());
    }

    @Transactional
    public void deleteEvent(Long eventId, User user) {

        Event event = findEvent(eventId);

        if (!event.getUser().getId().equals(user.getId())) {
            throw new InvalidAdminEventException(INVALID_ADMIN_EVENT);
        }

        eventQuery.deleteEventProduct(eventId);
        eventRepository.delete(event);

    }

    @Transactional
    public void deleteEventProduct(Long eventProductId, User user) {

        EventProduct eventProduct = eventProductRepository.findById(eventProductId)
            .orElseThrow(() -> new IllegalArgumentException("해당 이벤트 상품은 존재하지 않습니다."));

        eventProductRepository.delete(eventProduct);
    }

    private Event findEvent(Long eventId) {
        return eventRepository.findById(eventId)
            .orElseThrow(() -> new NotFoundException(NOT_FOUND));
    }

    private Coupon findCoupon(Long couponId) {
        return couponRepository.findById(couponId)
            .orElseThrow(() -> new NotFoundException(NOT_FOUND));
    }
}
