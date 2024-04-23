package com.kuku9.goods.domain.event.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.kuku9.goods.domain.coupon.dto.CouponResponse;
import com.kuku9.goods.domain.event.entity.Event;
import com.kuku9.goods.domain.event_product.dto.EventProductResponse;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Value;

@Getter
@Value
public class EventResponse {

    Long id;

    String title;

    String content;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    LocalDateTime openAt;

    CouponResponse coupon;
    List<EventProductResponse> eventProducts;

    public static EventResponse from(Event event, List<EventProductResponse> eventProducts) {
        return new EventResponse(
            event.getId(),
            event.getTitle(),
            event.getContent(),
            event.getOpenAt(),
            CouponResponse.from(event.getCoupon()),
            eventProducts
        );
    }
}
