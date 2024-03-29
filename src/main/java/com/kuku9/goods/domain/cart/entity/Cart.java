package com.kuku9.goods.domain.cart.entity;

import com.kuku9.goods.domain.product.entity.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "product_id")
    private Product product;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;

    @Column
    private int quantity;

    @Column
    private boolean status;

}
