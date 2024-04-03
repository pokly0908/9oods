package com.kuku9.goods.domain.order_product.entity;

import com.kuku9.goods.domain.product.entity.*;
import com.kuku9.goods.domain.order.entity.*;
import com.kuku9.goods.global.common.entity.*;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class OrderProduct extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "product_id")
	private Product product;

	@Column
	private int quantity;

	public OrderProduct(Order order, Product product, int quantity) {
		this.order = order;
		this.product = product;
		this.quantity = quantity;
	}
}
