package com.kuku9.goods.domain.seller.service;

import com.kuku9.goods.domain.product.entity.Product;
import com.kuku9.goods.domain.product.repository.ProductRepository;
import com.kuku9.goods.domain.seller.dto.ProductRegistRequestDto;
import com.kuku9.goods.domain.seller.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {

  private final SellerRepository sellerRepository;
  private final ProductRepository productRepository;


  @Override
  @Transactional
  public void createProduct(ProductRegistRequestDto requestDto) {
    Product product = new Product(requestDto);

    productRepository.save(product);
  }

}
