package com.kuku9.goods.domain.event.dto;

import com.kuku9.goods.domain.event.entity.Event;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EventResponse {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime openAt;
    private Long couponId;
    private List<Long> eventProducts;

    public static EventResponse from(Event event, List<Long> eventProducts) {
        return new EventResponse(
            event.getId(),
            event.getTitle(),
            event.getContent(),
            event.getOpenAt(),
            event.getCoupon().getId(),
            eventProducts
        );
    }
}
