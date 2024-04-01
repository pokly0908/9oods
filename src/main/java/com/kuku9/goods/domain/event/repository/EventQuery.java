package com.kuku9.goods.domain.event.repository;

import com.kuku9.goods.domain.event.dto.EventResponse;
import com.kuku9.goods.domain.event.dto.EventTitleResponse;
import java.util.List;

public interface EventQuery {

    EventResponse getEvent(Long eventId);

    List<EventTitleResponse> getEventTitles();

}
