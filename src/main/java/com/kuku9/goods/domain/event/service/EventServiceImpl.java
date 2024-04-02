package com.kuku9.goods.domain.event.service;

import static com.kuku9.goods.global.exception.ExceptionStatus.*;

import com.kuku9.goods.domain.event.dto.*;
import com.kuku9.goods.domain.event.entity.*;
import com.kuku9.goods.domain.event.repository.*;
import com.kuku9.goods.domain.event_product.dto.*;
import com.kuku9.goods.domain.event_product.entity.*;
import com.kuku9.goods.domain.event_product.repository.*;
import com.kuku9.goods.domain.product.repository.*;
import com.kuku9.goods.domain.seller.entity.*;
import com.kuku9.goods.domain.seller.repository.*;
import com.kuku9.goods.domain.user.entity.*;
import com.kuku9.goods.global.exception.*;
import java.util.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

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

        Event event = new Event(eventRequest.getTitle(), eventRequest.getContent(),
            eventRequest.getLimitNum(), eventRequest.getOpenAt());
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
        event.update(eventRequest.getTitle(), eventRequest.getContent(),
            eventRequest.getLimitNum(), eventRequest.getOpenAt());

        //todo: 생성자가 수정할 수 있도록 검증 처리 추가하기

        return event.getId();
    }

    @Transactional(readOnly = true)
    public EventResponse getEvent(Long eventId) {

        Event event = findEvent(eventId);
        List<ProductInfo> infos = eventQuery.getEventProductInfo(event.getId());
        return EventResponse.from(event, infos);
    }

    @Transactional(readOnly = true)
    public List<EventTitleResponse> getEventTitles() {

        return eventQuery.getEventTitles();
    }

    @Transactional
    public void deleteEvent(Long eventId, User user) {

        Event event = findEvent(eventId);

        //todo: 생성자가 삭제할 수 있도록 검증 처리 추가하기
        eventQuery.deleteEventProduct(eventId);
        eventRepository.delete(event);

    }

    @Transactional
    public void deleteEventProduct(Long eventProductId, User user) {

        EventProduct eventProduct = eventProductRepository.findById(eventProductId)
            .orElseThrow(() -> new IllegalArgumentException("해당 이벤트 상품은 존재하지 않습니다."));

        //todo: 생성자가 삭제할 수 있도록 검증 처리 추가하기

        eventProductRepository.delete(eventProduct);
    }

    private Event findEvent(Long eventId) {
        return eventRepository.findById(eventId)
            .orElseThrow(() -> new EventNotFoundException(NOT_FOUND_EVENT));
    }
}
