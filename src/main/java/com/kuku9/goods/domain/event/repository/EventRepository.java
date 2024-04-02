package com.kuku9.goods.domain.event.repository;

import com.kuku9.goods.domain.event.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

}
