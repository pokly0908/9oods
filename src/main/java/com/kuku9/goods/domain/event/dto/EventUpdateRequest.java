package com.kuku9.goods.domain.event.dto;

import jakarta.validation.constraints.*;
import java.time.*;
import lombok.*;
import org.springframework.format.annotation.*;

@Getter
public class EventUpdateRequest {

    @NotNull(message = "이벤트를 등록하시려면 제목을 입력하세요.")
    private String title;

    @NotNull(message = "이벤트를 등록하시려면 내용을 입력하세요.")
    private String content;

    private Long limitNum;

    @NotNull(message = "이벤트를 등록하시려면 오픈일자를 입력하세요.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate openAt;

}
