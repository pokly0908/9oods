package com.kuku9.goods.domain.order.entity;

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

    @Column
    private int totalPrice;

    public Order(User user, String address) {
        this.user = user;
        this.status = "결제완료";
        this.address = address;
    }

    public void updateStatus(String status) {
        this.status = status;
    }

    public void addTotalPrice(int totalPrice) {
        this.totalPrice += totalPrice;
    }

    public void discount(int discount) {
        this.totalPrice -= discount;
    }
}
