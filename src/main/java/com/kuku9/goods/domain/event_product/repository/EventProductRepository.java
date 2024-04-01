package com.kuku9.goods.domain.event_product.repository;

import com.kuku9.goods.domain.event_product.entity.EventProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventProductRepository extends JpaRepository<EventProduct, Long> {

}
