package com.kuku9.goods.domain.event.entity;

import com.kuku9.goods.domain.coupon.entity.Coupon;
import com.kuku9.goods.domain.event.dto.EventRequest;
import com.kuku9.goods.domain.event.dto.EventUpdateRequest;
import com.kuku9.goods.domain.user.entity.User;
import com.kuku9.goods.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;


@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE event SET deleted_at=CURRENT_TIMESTAMP where id=?")
@SQLRestriction("deleted_at IS NULL")
public class Event extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private LocalDateTime openAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    public Event(EventRequest request, User user) {
        this.title = request.getTitle();
        this.content = request.getContent();
        this.openAt = request.getOpenAt();
        this.user = user;
    }

    public void update(EventUpdateRequest request) {
        this.title = request.getTitle();
        this.content = request.getContent();
        this.openAt = request.getOpenAt();
    }

    public void addCoupon(Coupon coupon) {
        this.coupon = coupon;
    }
}
