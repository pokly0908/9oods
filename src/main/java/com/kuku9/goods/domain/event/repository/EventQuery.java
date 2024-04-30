package com.kuku9.goods.domain.event.repository;

import com.kuku9.goods.domain.event.dto.EventResponse;
import com.kuku9.goods.domain.event_product.dto.EventProductResponse;
import java.time.LocalDateTime;
import java.util.List;

public interface EventQuery {

    List<EventProductResponse> getEventProducts(List<Long> eventIds);

    void deleteEventProduct(Long eventId);

    LocalDateTime getOpenDate(Long couponId);

    List<EventResponse> getEvents();

    EventResponse getEvent(Long eventId);
}
