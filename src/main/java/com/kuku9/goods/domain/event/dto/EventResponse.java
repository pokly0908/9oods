package com.kuku9.goods.domain.event.dto;

import com.kuku9.goods.domain.event.entity.Event;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventResponse {

    private Long id;
    private String title;
    private String content;
    private Long limitNum;
    private LocalDate openAt;

    public static EventResponse from(Event event) {
        return new EventResponse(event.getId(), event.getTitle(), event.getContent(),
            event.getLimitNum(), event.getOpenAt());
    }
}
