package com.kuku9.goods.domain.event.dto;

import com.kuku9.goods.domain.event.entity.Event;
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

    public static EventResponse from(Event event) {
        return new EventResponse(event.getId(), event.getTitle(), event.getContent());
    }
}
