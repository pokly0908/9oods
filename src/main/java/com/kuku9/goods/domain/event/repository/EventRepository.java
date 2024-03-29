package com.kuku9.goods.domain.event.repository;

import com.kuku9.goods.domain.event.dto.EventResponse;
import com.kuku9.goods.domain.event.dto.EventTitleResponse;
import com.kuku9.goods.domain.event.entity.Event;

import java.util.List;

public interface EventRepository {

    Event save(Event event);

    Event findById(Long eventId);

    EventResponse getEvent(Long eventId);

    List<EventTitleResponse> getEventTitles();

    void delete(Event event);
}
