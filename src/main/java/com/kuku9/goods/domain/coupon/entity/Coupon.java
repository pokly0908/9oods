package com.kuku9.goods.domain.coupon.entity;

import com.kuku9.goods.domain.coupon.dto.CouponRequest;
import jakarta.persistence.*;
import java.time.LocalDate;
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
    private LocalDate expirationDate;

    @Column
    private int quantity;

    @Column
    private String category;

    public Coupon(CouponRequest request) {
        this.expirationDate = request.getExpirationDate();
        this.quantity = request.getQuantity();
        this.category = request.getCategory();
    }

    public void decrease() {
        this.quantity -= 1;
    }
}
