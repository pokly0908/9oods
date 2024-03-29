package com.kuku9.goods.domain.event.service;

import com.kuku9.goods.domain.event.dto.EventResponse;
import com.kuku9.goods.domain.event.dto.EventTitleResponse;

import java.util.List;

public interface EventService {

    Long createEvent(String title, String content, Long fileId);

    Long updateEvent(Long eventId, String title, String content, Long fileId);

    EventResponse getEvent(Long eventId);

    List<EventTitleResponse> getEventTitles();

    void deleteEvent(Long eventId);
}
