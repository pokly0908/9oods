package com.kuku9.goods.domain.seller.service;

import com.kuku9.goods.domain.product.entity.Product;
import com.kuku9.goods.domain.product.repository.ProductRepository;
import com.kuku9.goods.domain.seller.dto.ProductRegistRequestDto;
import com.kuku9.goods.domain.seller.dto.ProductUpdateRequestDto;
import com.kuku9.goods.domain.seller.entity.Seller;
import com.kuku9.goods.domain.seller.repository.SellerRepository;
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
    public Long createProduct(ProductRegistRequestDto requestDto, CustomUserDetails userDetails) {
        Seller seller = findSeller(userDetails);
        if (seller == null) {
            throw new IllegalArgumentException("셀러만 상품을 등록할 수 있습니다. 셀러 신청하세요.");
        }
        Product product = new Product(requestDto, seller);

        productRepository.save(product);

        return product.getSeller().getId();
    }

    @Override
    @Transactional
    public Long orderProductStatus(Long productId, CustomUserDetails userDetails) {
        Seller seller = findSeller(userDetails);
        if (seller == null) {
            throw new IllegalArgumentException("셀러만 상품을 등록할 수 있습니다. 셀러 신청하세요.");
        }
        Product product = findProduct(productId, seller);
        if (product == null) {
            throw new IllegalArgumentException("상품이 존재하지 않습니다.");
        }

        product.updateOrderStatus(product.getStatus());

        return product.getSeller().getId();
        // todo :: 카트 기능 구현 시 카트에 담겨져 있는 상품들 상태를 바꾸게 변경
    }

    @Override
    @Transactional
    public Long updateProduct(
        Long productId, ProductUpdateRequestDto requestDto, CustomUserDetails userDetails) {
        Seller seller = findSeller(userDetails);
        if (seller == null) {
            throw new IllegalArgumentException("셀러만 상품 정보를 수정할 수 있습니다. 셀러 신청하세요.");
        }
        Product product = findProduct(productId, seller);
        if (product == null) {
            throw new IllegalArgumentException("상품이 존재하지 않습니다.");
        }

        product.updateProduct(requestDto);

        return product.getSeller().getId();
    }

    private Seller findSeller(CustomUserDetails userDetails) {
        return sellerRepository.findByUserId(userDetails.getUser().getId());
    }

    private Product findProduct(Long productId, Seller seller) {
        return productRepository.findByIdAndSellerId(productId, seller.getId());
    }

}
