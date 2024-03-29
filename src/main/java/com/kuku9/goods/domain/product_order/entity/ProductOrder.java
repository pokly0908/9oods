package com.kuku9.goods.domain.product_order.entity;

import com.kuku9.goods.domain.order_product.entity.OrderProduct;
import com.kuku9.goods.domain.user.entity.User;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_order")
public class ProductOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

  @ManyToOne
  private User user;

  @OneToMany
  private List<OrderProduct> orderProducts = new ArrayList<>();

    @Column
    private String status;


}
