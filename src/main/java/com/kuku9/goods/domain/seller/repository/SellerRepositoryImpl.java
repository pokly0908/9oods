package com.kuku9.goods.domain.seller.repository;

import com.kuku9.goods.domain.seller.entity.Seller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SellerRepositoryImpl implements SellerRepository {

  private final SellerJpaRepository sellerJpaRepository;


  @Override
  public Seller findByUserId(Long id) {
    return sellerJpaRepository.findByUserId(id);
  }
}
