package com.kuku9.goods.domain.event_product.repository;

import com.kuku9.goods.domain.event_product.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface EventProductRepository extends JpaRepository<EventProduct, Long> {

}
