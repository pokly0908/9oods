package com.kuku9.goods.domain.product.repository;

import static com.kuku9.goods.domain.product.entity.QProduct.product;

import com.kuku9.goods.domain.product.entity.Product;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Product save(Product product) {
        return productJpaRepository.save(product);
    }

    @Override
    public Optional<Product> findByIdAndSellerId(Long productId, Long id) {
        return productJpaRepository.findByIdAndSellerId(productId, id);
    }

    @Override
    public Optional<Product> findById(Long productId) {
        return Optional.ofNullable(jpaQueryFactory
            .selectFrom(product)
            .where(product.id.eq(productId))
            .fetchOne());
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productJpaRepository.findAll(pageable);
    }

    @Override
    public Page<Product> findBySellerId_DomainName(String domainName, Pageable pageable) {
        return productJpaRepository.findBySellerId_DomainName(domainName, pageable);
    }

    @Override
    public List<Product> findBySellerId(Long sellerId) {
        return productJpaRepository.findBySellerId(sellerId);
    }
}
