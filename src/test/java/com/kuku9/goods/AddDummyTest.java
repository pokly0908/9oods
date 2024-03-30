package com.kuku9.goods;

import com.kuku9.goods.domain.product.entity.Product;
import com.kuku9.goods.domain.product.repository.ProductJpaRepository;
import com.kuku9.goods.domain.seller.entity.Seller;
import com.kuku9.goods.domain.seller.repository.SellerJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AddDummyTest {

  @Autowired
  private SellerJpaRepository sellerJpaRepository;

  @Autowired
  private ProductJpaRepository productJpaRepository;

  @Test
  public void testSellerDummyData() {
    for (int i = 1; i <= 10; i++) {
      Seller seller = new Seller();
      seller.setBrandName("brand_name" + i);
      seller.setDomainNmae("domain_name" + i);
      seller.setIntroduce("brand_introduce" + i);
      seller.setEmail("email" + i);
      seller.setPhoneNumber("phone_number" + i);

      sellerJpaRepository.save(seller);
    }
  }

  @Test
  public void testProductDummyData() {
    for (int i = 1; i <= 1000; i++) {
      Product product = new Product();
      product.setName("product_name" + i);
      product.setDescription("product_description" + i);
      product.setPrice((int)(i*(Math.random()+Math.random()*10000)));

      productJpaRepository.save(product);
    }
  }
}
