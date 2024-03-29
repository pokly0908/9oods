package com.kuku9.goods.domain.seller.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SellerRepositoryImpl implements SellerRepository {

  private final SellerJpaRepository sellerJpaRepository;


}
