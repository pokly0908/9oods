package com.kuku9.goods.domain.order_product.entity;

import com.kuku9.goods.domain.order_product.dto.OrderProductRequest;
import com.kuku9.goods.domain.product.entity.Product;
import com.kuku9.goods.domain.user.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "product_id")
    private Product product;
    ;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private int quantity;

    @Column
    private String status;

    public OrderProduct(Product product, OrderProductRequest request, User user, String status) {
        this.user = user;
        this.product = product;
        this.quantity = request.getQuantity();
        this.status = status;
    }

    public void update(Product product, OrderProductRequest request) {
        this.product = product;
        this.quantity = request.getQuantity();
    }
}
