package com.kuku9.goods.domain.event.dto;

import com.kuku9.goods.domain.event.entity.Event;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EventResponse {

    private Long id;
    private String title;
    private String content;
    private Long limitNum;
    private LocalDate openAt;
    private List<ProductInfo> eventProducts;

    public static EventResponse from(Event event, List<ProductInfo> eventProducts) {
        return new EventResponse(
            event.getId(),
            event.getTitle(),
            event.getContent(),
            event.getLimitNum(),
            event.getOpenAt(),
            eventProducts
        );
    }
}
