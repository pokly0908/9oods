package com.kuku9.goods.domain.seller.repository;

import com.kuku9.goods.domain.seller.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller, Long> {
    boolean existsByUserId(Long userId);

    boolean existsByBrandName(String brandName);

    boolean existsByDomainName(String domainName);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);
}