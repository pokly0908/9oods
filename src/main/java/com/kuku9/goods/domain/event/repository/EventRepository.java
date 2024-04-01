package com.kuku9.goods.domain.event.repository;

import com.kuku9.goods.domain.event.entity.Event;
import java.util.Optional;
import org.hibernate.annotations.processing.Find;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

	Optional<Event> findById(Long id);
}
