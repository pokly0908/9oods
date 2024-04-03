package com.kuku9.goods.domain.seller.service;

import com.kuku9.goods.domain.order_product.entity.OrderProduct;
import com.kuku9.goods.domain.order_product.repository.OrderProductRepository;
import com.kuku9.goods.domain.product.entity.Product;
import com.kuku9.goods.domain.product.repository.ProductRepository;
import com.kuku9.goods.domain.seller.dto.request.ProductRegistRequest;
import com.kuku9.goods.domain.seller.dto.request.ProductUpdateRequest;
import com.kuku9.goods.domain.seller.dto.response.SellProductResponse;
import com.kuku9.goods.domain.seller.dto.response.SellProductStatisticsResponse;
import com.kuku9.goods.domain.seller.entity.Seller;
import com.kuku9.goods.domain.seller.repository.SellerRepository;
import com.kuku9.goods.domain.user.entity.User;
import com.kuku9.goods.global.exception.ExceptionStatus;
import com.kuku9.goods.global.exception.InvalidSellerEventException;
import java.time.LocalDate;
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
    public Long createProduct(ProductRegistRequest requestDto, User user) {
        Seller seller = findSeller(user);

        Product product = new Product(requestDto, seller);

        Product saveProduct = productRepository.save(product);

        return saveProduct.getSeller().getId();
    }

    @Override
    @Transactional
    public Long orderProductStatus(Long productId, User user) {
        Seller seller = findSeller(user);

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
        Long productId, ProductUpdateRequest requestDto, User user) {
        Seller seller = findSeller(user);

        Product product = findProduct(productId, seller);
        if (product == null) {
            throw new IllegalArgumentException("상품이 존재하지 않습니다.");
        }

        product.updateProduct(requestDto);

        return product.getSeller().getId();
    }

    @Override
    @Transactional(readOnly = true)
    public List<SellProductResponse> getSellingProduct(
        User user, LocalDate startDate, LocalDate endDate) {
        Seller seller = findSeller(user);

        List<OrderProduct> orderProductList = new ArrayList<>();
        for (Product product : productRepository.findBySellerId(seller.getId())) {
            List<OrderProduct> orderProducts =
                orderProductRepository.findByProductAndCreatedAtBetween(
                    product,
                    startDate.atStartOfDay(),
                    endDate.plusDays(1).atStartOfDay());
            // 변경된 부분: orderDate -> createdAt로 수정

            if (orderProducts != null) {
                orderProductList.addAll(orderProducts);
            }
        }

        List<SellProductResponse> responseDtoList = new ArrayList<>();
        long totalPrice = 0L;
        for (OrderProduct orderProduct : orderProductList) {
            long productTotalPrice =
                orderProduct.getProduct().getPrice() * orderProduct.getQuantity();
            responseDtoList.add(
                new SellProductResponse(
                    orderProduct.getProduct().getName(),
                    orderProduct.getProduct().getPrice(),
                    orderProduct.getQuantity(),
                    productTotalPrice));
        }

        return responseDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public SellProductStatisticsResponse getSellProductStatistics(User user) {
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

        return new SellProductStatisticsResponse(statisticsPrice);
    }

    private Seller findSeller(User user) {
        return sellerRepository.findByUserId(user.getId()).orElseThrow(() ->
            new InvalidSellerEventException(ExceptionStatus.INVALID_SELLER_EVENT));
    }

    private Product findProduct(Long productId, Seller seller) {
        return productRepository.findByIdAndSellerId(productId, seller.getId());
    }

    @Override
    public Seller save(Seller seller) {
        Seller savedSeller = sellerRepository.save(seller);
        return savedSeller;
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
