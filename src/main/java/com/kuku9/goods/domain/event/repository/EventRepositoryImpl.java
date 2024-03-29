package com.kuku9.goods.domain.event.repository;

import com.kuku9.goods.domain.event.dto.EventResponse;
import com.kuku9.goods.domain.event.dto.EventTitleResponse;
import com.kuku9.goods.domain.event.entity.Event;
import com.kuku9.goods.domain.event.entity.QEvent;
import com.kuku9.goods.domain.file.entity.QFile;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EventRepositoryImpl implements EventRepository {

  private final EventJpaRepository eventJpaRepository;
  private final JPAQueryFactory jpaQueryFactory;

  public Event save(Event event) {
    return eventJpaRepository.save(event);
  }

  public void delete(Event event) {
    eventJpaRepository.delete(event);
  }

  public Event findById(Long eventId) {
    return eventJpaRepository.findById(eventId)
        .orElseThrow(() -> new IllegalArgumentException("해당 이벤트는 존재하지 않습니다."));
  }

  public EventResponse getEvent(Long eventId) {
    List<EventResponse> result = jpaQueryFactory.select(
            Projections.fields(EventResponse.class, QEvent.event.id, QEvent.event.title,
                QEvent.event.content, QFile.file.url))
        .from(QEvent.event)
        .leftJoin(QFile.file).fetchJoin()
        .on(QEvent.event.fileId.eq(QFile.file.id))
        .where(QEvent.event.id.eq(eventId))
        .fetch();

    return result.get(0);
  }

  public List<EventTitleResponse> getEventTitles() {
    return jpaQueryFactory.select(
            Projections.fields(EventTitleResponse.class, QEvent.event.title))
        .from(QEvent.event)
        .fetch();
  }

}
