package com.kuku9.goods.domain.event.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.kuku9.goods.domain.coupon.dto.CouponResponse;
import com.kuku9.goods.domain.event.entity.Event;
import com.kuku9.goods.domain.event_product.dto.EventProductResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Value;

@Getter
@Value
public class AllEventResponse {

    Long id;

    String title;

    String content;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    LocalDateTime openAt;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    LocalDate expiration;

    int quantity;

    List<EventProductResponse> eventProducts;

    public static AllEventResponse from(EventResponse eventResponse, List<EventProductResponse> eventProducts) {
        return new AllEventResponse(
            eventResponse.getId(),
            eventResponse.getTitle(),
            eventResponse.getContent(),
            eventResponse.getOpenAt(),
            eventResponse.getExpirationDate(),
            eventResponse.getQuantity(),
            eventProducts
        );
    }
}
