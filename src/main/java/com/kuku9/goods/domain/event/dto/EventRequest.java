package com.kuku9.goods.domain.event.dto;

import com.kuku9.goods.domain.event_product.dto.*;
import jakarta.validation.constraints.*;
import java.time.*;
import java.util.*;
import lombok.*;
import org.springframework.format.annotation.*;

@Getter
public class EventRequest {

	@NotNull(message = "이벤트를 등록하시려면 제목을 입력하세요.")
	private String title;

	@NotNull(message = "이벤트를 등록하시려면 내용을 입력하세요.")
	private String content;

	private Long limitNum;

	@NotNull(message = "이벤트를 등록하시려면 오픈일자를 입력하세요.")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate openAt;

	private List<EventProductRequest> eventProducts;

}
