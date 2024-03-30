package com.kuku9.goods.domain.order_product.entity;

import com.kuku9.goods.domain.order_product.dto.OrderProductRequest;
import com.kuku9.goods.domain.product.entity.Product;
import com.kuku9.goods.domain.product_order.entity.ProductOrder;
import com.kuku9.goods.domain.user.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_order_id")
    private ProductOrder productOrder;

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "product_id")
  private Product product;

  @Column
  private int quantity;

    public OrderProduct(ProductOrder productOrder, Product product, int quantity) {
        this.productOrder = productOrder;
        this.product = product;
        this.quantity = quantity;
    }
}
