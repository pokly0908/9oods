package com.kuku9.goods.domain.order.entity;

import com.kuku9.goods.domain.user.entity.*;
import com.kuku9.goods.global.common.entity.*;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "`order`")
public class Order extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private User user;

	@Column
	private String status;

	@Column
	private String address;

	public Order(User user, String address) {
		this.user = user;
		this.status = "결제완료";
		this.address = address;
	}

	public void updateStatus(String status) {
		this.status = status;
	}
}
