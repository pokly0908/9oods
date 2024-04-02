package com.kuku9.goods.domain.event.repository;

import com.kuku9.goods.domain.event.dto.EventResponse;
import com.kuku9.goods.domain.event.dto.EventTitleResponse;
import com.kuku9.goods.domain.event.dto.ProductInfo;
import java.util.List;

public interface EventQuery {

    List<Long> getEventProductInfo(Long eventId);
    void deleteEventProduct(Long eventId);
}
