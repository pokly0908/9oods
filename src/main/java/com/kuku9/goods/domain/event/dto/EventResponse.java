package com.kuku9.goods.domain.event.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Value;

@Getter
@Value
public class EventResponse {

	Long id;
	String title;
	String content;
	LocalDateTime openAt;
	LocalDate expirationDate;
	int quantity;

}
