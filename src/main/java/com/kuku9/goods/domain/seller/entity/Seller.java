package com.kuku9.goods.domain.seller.entity;

import com.kuku9.goods.domain.product.entity.Product;
import com.kuku9.goods.domain.user.entity.User;
import com.kuku9.goods.global.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table
@Getter
@NoArgsConstructor
public class Seller extends BaseEntity {

  @Id
  @GeneratedValue
  private Long id;

  @Column
  private String boardName;

  @Column
  private String domainNmae;

  @Column
  private String introduce;

  @Column
  private String email;

  @Column
  private String phone_number;

  @Column
  private String status;

  @OneToOne
  @JoinColumn(name = "userId")
  private User user;

  @OneToMany(mappedBy = "seller")
  private List<Product> products = new ArrayList<>();


}
