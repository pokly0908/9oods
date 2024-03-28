package com.kuku9.goods.domain.event.repository;

import com.kuku9.goods.domain.event.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventJpaRepository extends JpaRepository<Event, Long> {

}
