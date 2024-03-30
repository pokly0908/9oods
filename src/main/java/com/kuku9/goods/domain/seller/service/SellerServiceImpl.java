package com.kuku9.goods.domain.seller.service;

import com.kuku9.goods.domain.product.entity.Product;
import com.kuku9.goods.domain.product.repository.ProductRepository;
import com.kuku9.goods.domain.seller.dto.ProductRegistRequestDto;
import com.kuku9.goods.domain.seller.entity.Seller;
import com.kuku9.goods.domain.seller.repository.SellerRepository;
import com.kuku9.goods.domain.user.entity.UserRoleEnum;
import com.kuku9.goods.global.security.CustomUserDetails;
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
  public void createProduct(ProductRegistRequestDto requestDto, CustomUserDetails userDetails) {
    if (!userDetails.getUser().getRole().equals(UserRoleEnum.SELLER)) {
      throw new IllegalArgumentException("셀러만 상품 등록이 가능합니다.");
    }
    Product product = new Product(requestDto);

    productRepository.save(product);
  }

  @Override
  @Transactional
  public void orderProductStatus(Long productId, CustomUserDetails userDetails) {
    if (!userDetails.getUser().getRole().equals(UserRoleEnum.SELLER)) {
      throw new IllegalArgumentException("셀러만 상품 등록이 가능합니다.");
    }
    Seller seller = sellerRepository.findByUserId(userDetails.getUser().getId());
    Product product = productRepository.findByIdAndSellerId(productId, seller.getId());

    product.updateOrderStatus(product.getStatus());
  }

}
