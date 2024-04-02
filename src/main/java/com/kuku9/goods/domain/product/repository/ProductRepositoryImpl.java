package com.kuku9.goods.domain.product.repository;

import com.kuku9.goods.domain.product.entity.Product;
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

	@Override
	public void save(Product product) {
		productJpaRepository.save(product);
	}

	@Override
	public Product findByIdAndSellerId(Long productId, Long id) {
		return productJpaRepository.findByIdAndSellerId(productId, id);
	}

	@Override
	public Optional<Product> findById(Long productId) {
		return productJpaRepository.findById(productId);
	}

	@Override
	public Page<Product> findAll(Pageable pageable) {
		return productJpaRepository.findAll(pageable);
	}

	@Override
	public Page<Product> findBySellerId(Long sellerId, Pageable pageable) {
		return productJpaRepository.findBySellerId(sellerId, pageable);
	}

	@Override
	public List<Product> findBySellerId(Long sellerId) {
		return productJpaRepository.findBySellerId(sellerId);
	}
}
