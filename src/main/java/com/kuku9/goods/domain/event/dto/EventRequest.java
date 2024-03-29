package com.kuku9.goods.domain.event.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class EventRequest {

    @NotNull(message = "제목을 입력하세요.")
    private String title;

    @NotNull(message = "내용을 입력하세요.")
    private String content;

    private Long fileId;
}
