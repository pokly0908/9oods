package com.kuku9.goods.domain.coupon.entity;

import com.kuku9.goods.domain.coupon.dto.CouponRequest;
import com.kuku9.goods.domain.event.entity.Event;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Coupon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private LocalDateTime expirationDate;

	@Column
	private int quantity;

	public Coupon(CouponRequest request) {
		this.expirationDate = request.getExpirationDate();
		this.quantity = request.getQuantity();
	}

	public void decrease() {
		this.quantity -= 1;
	}
}
