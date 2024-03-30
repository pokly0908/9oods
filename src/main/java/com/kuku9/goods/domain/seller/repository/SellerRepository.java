package com.kuku9.goods.domain.seller.repository;

import com.kuku9.goods.domain.seller.entity.Seller;

public interface SellerRepository {

  Seller findByUserId(Long id);
}
