package com.kuku9.goods.domain.event.repository;

import com.kuku9.goods.domain.event_product.entity.EventProduct;
import java.time.LocalDateTime;
import java.util.List;

public interface EventQuery {

    List<EventProduct> getEventProducts(List<Long> eventIds);

    void deleteEventProduct(Long eventId);

    LocalDateTime getOpenDate(Long couponId);
}
