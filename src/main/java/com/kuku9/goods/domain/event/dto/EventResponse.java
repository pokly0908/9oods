package com.kuku9.goods.domain.event.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@Getter
@Value
public class EventResponse {

	Long id;
	String title;
	String content;
	LocalDateTime openAt;
	Long couponId;
	LocalDate expirationDate;
	int quantity;

}
