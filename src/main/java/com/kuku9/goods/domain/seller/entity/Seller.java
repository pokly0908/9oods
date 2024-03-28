package com.kuku9.goods.domain.seller.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table
@Getter
@NoArgsConstructor
public class Seller {

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
}
