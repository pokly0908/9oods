package com.kuku9.goods.domain.issued_coupon.entity;

import com.kuku9.goods.domain.coupon.entity.Coupon;
import com.kuku9.goods.domain.user.entity.User;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SQLRestriction("deleted_at IS NULL")
public class IssuedCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @Column
    private LocalDate deletedAt;

    public IssuedCoupon(User user, Coupon coupon) {
        this.user = user;
        this.coupon = coupon;
    }

}
