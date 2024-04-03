package com.kuku9.goods.domain.product_order.entity;

import com.kuku9.goods.domain.user.entity.User;
import com.kuku9.goods.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_order")
public class ProductOrder extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private User user;

	@Column
	private String status;

	@Column
	private String address;

	public ProductOrder(User user, String address) {
		this.user = user;
		this.status = "결제완료";
		this.address = address;
	}

	public void updateStatus(String status) {
		this.status = status;
	}
}
