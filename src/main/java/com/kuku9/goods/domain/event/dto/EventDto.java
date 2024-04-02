package com.kuku9.goods.domain.event.dto;

import com.kuku9.goods.domain.event.entity.Event;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class EventDto {
	private Long id;
	private String title;
	private String content;
	private Long limitNum;
	private LocalDate openAt;

	public EventDto(Event event) {
		this.id = event.getId();
		this.title = event.getTitle();
		this.content = event.getContent();
		this.limitNum = event.getLimitNum();
		this.openAt = event.getOpenAt();
	}
}
