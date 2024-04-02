package com.kuku9.goods.domain.event.repository;

import com.kuku9.goods.domain.event.dto.EventResponse;
import com.kuku9.goods.domain.event.dto.EventTitleResponse;
import com.kuku9.goods.domain.event.dto.ProductInfo;
import java.util.List;

public interface EventQuery {

    List<ProductInfo> getEventProductInfo(Long eventId);
    List<EventTitleResponse> getEventTitles();
    void deleteEventProduct(Long eventId);
}
