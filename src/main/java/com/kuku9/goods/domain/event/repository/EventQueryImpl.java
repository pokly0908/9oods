package com.kuku9.goods.domain.event.repository;

import com.kuku9.goods.domain.event.dto.EventResponse;
import com.kuku9.goods.domain.event.dto.EventTitleResponse;
import com.kuku9.goods.domain.event.entity.QEvent;
import com.kuku9.goods.domain.file.entity.QFile;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EventQueryImpl implements EventQuery {

    private final JPAQueryFactory jpaQueryFactory;

    public List<EventTitleResponse> getEventTitles() {
        return jpaQueryFactory.select(
                Projections.fields(EventTitleResponse.class, QEvent.event.title))
            .from(QEvent.event)
            .fetch();
    }

}
