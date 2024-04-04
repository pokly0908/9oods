package com.kuku9.goods.domain.event.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.kuku9.goods.domain.event.entity.Event;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EventResponse {

    private Long id;

    private String title;

    private String content;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
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
