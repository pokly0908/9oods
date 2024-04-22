package com.kuku9.goods.domain.event.dto;

import com.kuku9.goods.domain.event_product.dto.EventProductRequest;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Value
public class EventRequest {

    @NotNull(message = "이벤트를 등록하시려면 제목을 입력하세요.")
    String title;

    @NotNull(message = "이벤트를 등록하시려면 내용을 입력하세요.")
    String content;

    @NotNull(message = "이벤트를 등록하시려면 오픈일자를 입력하세요.")
    @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ss")
    LocalDateTime openAt;

    List<EventProductRequest> eventProducts;

    Long couponId;

}
