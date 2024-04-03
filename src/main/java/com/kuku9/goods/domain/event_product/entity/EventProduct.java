package com.kuku9.goods.domain.event_product.entity;

import com.kuku9.goods.domain.event.entity.*;
import com.kuku9.goods.domain.product.entity.*;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class EventProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "event_id")
	private Event event;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;

	public EventProduct(Event event, Product product) {
		this.event = event;
		this.product = product;
	}
}
