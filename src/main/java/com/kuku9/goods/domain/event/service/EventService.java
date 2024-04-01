package com.kuku9.goods.domain.event.service;

import com.kuku9.goods.domain.event.dto.EventRequest;
import com.kuku9.goods.domain.event.dto.EventResponse;
import com.kuku9.goods.domain.event.dto.EventTitleResponse;
import com.kuku9.goods.domain.user.entity.User;
import java.nio.file.AccessDeniedException;
import java.util.List;

public interface EventService {

    /**
     * 이벤트 등록
     *
     * @param request 이벤트 등록에 필요한 정보
     * @return eventId
	 */
    Long createEvent(EventRequest request);

    /**
     * 이벤트 수정
     *
     * @param request 이벤트 수정에 필요한 정보
     * @param user 유저
     * @return eventId
     */
    Long updateEvent(Long eventId, EventRequest request, User user);

    /**
     * 이벤트 단건 조회
     *
     * @param eventId 이벤트 Id
     * @return EventResponse
     */
    EventResponse getEvent(Long eventId);

    /**
     * 이벤트 제목 리스트 조회
     *
     * @return List<EventTitleResponse>
     */
    List<EventTitleResponse> getEventTitles();

    /**
     * 이벤트 삭제
     *
     * @param eventId 이벤트 Id
     * @param user 유저
     */
    void deleteEvent(Long eventId, User user);
}
