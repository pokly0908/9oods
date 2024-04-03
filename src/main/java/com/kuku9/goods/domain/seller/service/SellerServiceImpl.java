package com.kuku9.goods.domain.seller.service;

import com.kuku9.goods.domain.order_product.entity.OrderProduct;
import com.kuku9.goods.domain.order_product.repository.OrderProductRepository;
import com.kuku9.goods.domain.product.entity.Product;
import com.kuku9.goods.domain.product.repository.ProductRepository;
import com.kuku9.goods.domain.seller.dto.ProductRegistRequestDto;
import com.kuku9.goods.domain.seller.dto.ProductUpdateRequestDto;
import com.kuku9.goods.domain.seller.dto.SellProductStatisticsResponseDto;
import com.kuku9.goods.domain.seller.dto.SellingProductResponseDto;
import com.kuku9.goods.domain.seller.entity.Seller;
import com.kuku9.goods.domain.seller.repository.SellerRepository;
import com.kuku9.goods.domain.user.entity.User;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;
    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;

    @Override
    @Transactional
    public Long createProduct(ProductRegistRequestDto requestDto, User user) {
        Seller seller = findSeller(user);
        if (seller == null) {
            throw new IllegalArgumentException("셀러만 상품을 등록할 수 있습니다. 셀러 신청하세요.");
        }
        Product product = new Product(requestDto, seller);

        productRepository.save(product);

        return product.getSeller().getId();
    }

    @Override
    @Transactional
    public Long orderProductStatus(Long productId, User user) {
        Seller seller = findSeller(user);
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
        Long productId, ProductUpdateRequestDto requestDto, User user) {
        Seller seller = findSeller(user);
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

    @Override
    @Transactional(readOnly = true)
    public List<SellingProductResponseDto> getSellingProduct(User user) {
        Seller seller = findSeller(user);

        List<OrderProduct> orderProductList = new ArrayList<>();
        for (Product product : productRepository.findBySellerId(seller.getId())) {
            OrderProduct orderProduct = orderProductRepository.findByProductId(product.getId());
            if (orderProduct != null) {
                orderProductList.add(orderProduct);
            }
        }

        List<SellingProductResponseDto> responseDtoList = new ArrayList<>();
        long totalPrice = 0L;
        for (OrderProduct orderProduct : orderProductList) {
            long productTotalPrice =
                orderProduct.getProduct().getPrice() * orderProduct.getQuantity();
            totalPrice += productTotalPrice;
            responseDtoList.add(
                new SellingProductResponseDto(
                    orderProduct.getProduct().getName(),
                    orderProduct.getProduct().getPrice(),
                    orderProduct.getQuantity(),
                    productTotalPrice,
                    totalPrice));
        }

        return responseDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public SellProductStatisticsResponseDto getSellProductStatistics(User user) {
        Seller seller = findSeller(user);

        List<OrderProduct> orderProductList = new ArrayList<>();
        for (Product product : productRepository.findBySellerId(seller.getId())) {
            OrderProduct orderProduct = orderProductRepository.findByProductId(product.getId());
            if (orderProduct != null) {
                orderProductList.add(orderProduct);
            }
        }

        long totalPrice = 0L;
        long statisticsPrice = 0L;
        for (OrderProduct orderProduct : orderProductList) {
            long productTotalPrice =
                (orderProduct.getProduct().getPrice() * orderProduct.getQuantity());
            totalPrice += productTotalPrice;
        }
        statisticsPrice = totalPrice / orderProductList.size();

        return new SellProductStatisticsResponseDto(statisticsPrice);
    }


    private Seller findSeller(User user) {
        return sellerRepository.findByUserId(user.getId());
    }

    private Product findProduct(Long productId, Seller seller) {
        return productRepository.findByIdAndSellerId(productId, seller.getId());
    }

    @Override
    public Seller save(Seller seller) {
        return sellerRepository.save(seller);
    }

    @Override
    public Boolean checkSellerExistsByUserId(Long userId) {
        return sellerRepository.existsByUserId(userId);
    }

    @Override
    public Boolean checkBrandNameExist(String brandName) {
        return sellerRepository.existsByBrandName(brandName);
    }

    @Override
    public Boolean checkDomainNameExist(String domainName) {
        return sellerRepository.existsByDomainName(domainName);
    }

    @Override
    public Boolean checkEmailExist(String email) {
        return sellerRepository.existsByEmail(email);
    }

    @Override
    public Boolean checkPhoneNumberExist(String phoneNumber) {
        return sellerRepository.existsByPhoneNumber(phoneNumber);
    }

}
