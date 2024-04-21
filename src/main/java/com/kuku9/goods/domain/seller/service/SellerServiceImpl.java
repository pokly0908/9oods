package com.kuku9.goods.domain.seller.service;

import static com.kuku9.goods.global.exception.ExceptionStatus.INVALID_PRODUCT_EVENT;
import static com.kuku9.goods.global.exception.ExceptionStatus.INVALID_SELLER_EVENT;

import com.kuku9.goods.domain.product.entity.Product;
import com.kuku9.goods.domain.product.repository.ProductRepository;
import com.kuku9.goods.domain.search.document.ProductDocument;
import com.kuku9.goods.domain.search.dto.ProductSearchResponse;
import com.kuku9.goods.domain.search.repository.ProductSearchRepository;
import com.kuku9.goods.domain.seller.dto.request.ProductQuantityRequest;
import com.kuku9.goods.domain.seller.dto.request.ProductRegistRequest;
import com.kuku9.goods.domain.seller.dto.request.ProductUpdateRequest;
import com.kuku9.goods.domain.seller.dto.response.SellerCheckResponse;
import com.kuku9.goods.domain.seller.dto.response.SoldProductQuantityResponse;
import com.kuku9.goods.domain.seller.dto.response.SoldProductResponse;
import com.kuku9.goods.domain.seller.dto.response.SoldProductSumPriceResponse;
import com.kuku9.goods.domain.seller.entity.Seller;
import com.kuku9.goods.domain.seller.repository.SellerQuery;
import com.kuku9.goods.domain.seller.repository.SellerRepository;
import com.kuku9.goods.domain.user.entity.User;
import com.kuku9.goods.global.exception.InvalidProductEventException;
import com.kuku9.goods.global.exception.InvalidSellerEventException;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;
    private final ProductRepository productRepository;
    private final SellerQuery sellerQuery;
    private final ProductSearchRepository productSearchRepository;

    @Override
    @Transactional
    public Long createProduct(ProductRegistRequest requestDto, User user) {
        Seller seller = findSeller(user);

        Product product = new Product(requestDto, seller);

        Product saveProduct = productRepository.save(product);

        ProductDocument productDocument = new ProductDocument(
            seller.getId(), saveProduct.getId(),
            requestDto.getProductName(), requestDto.getProductDescription(),
            requestDto.getProductPrice(), requestDto.getProductQuantity());

        productSearchRepository.save(productDocument);

        return saveProduct.getSeller().getId();
    }

    @Override
    @Transactional
    @CacheEvict(value = "productCache", key = "#productId")
    public Long updateProductStatus(Long productId, User user) {
        Seller seller = findSeller(user);

        Product product = findProduct(productId, seller);

        product.updateOrderStatus(product.getStatus());

        return product.getSeller().getId();
    }

    @Override
    @Transactional
    public Long updateProductQuantity(Long productId, ProductQuantityRequest request, User user) {
        Seller seller = findSeller(user);

        Product product = findProduct(productId, seller);

        product.updateQuantitySeller(request);

        return product.getSeller().getId();
    }

    @Override
    @Transactional
    @CacheEvict(value = "productCache", key = "#productId")
    public Long updateProduct(
        Long productId, ProductUpdateRequest requestDto, User user) {
        String productName, productDescription;
        int price, quantity;

        Seller seller = findSeller(user);

        Product product = findProduct(productId, seller);

        productSearchRepository.deleteByProductId(product.getId());

        if (requestDto.getName().isEmpty()) {
            productName = product.getName();
        } else {
            productName = requestDto.getName();
        }
        if (requestDto.getDescription().isEmpty()) {
            productDescription = product.getDescription();
        } else {
            productDescription = requestDto.getDescription();
        }
        if (requestDto.getPrice() <= 0) {
            price = product.getPrice();
        } else {
            price = requestDto.getPrice();
        }
        if (requestDto.getQuantity() <= 0) {
            quantity = product.getQuantity();
        } else {
            quantity = requestDto.getQuantity();
        }

        product.updateProduct(
            productName, productDescription,
            price, quantity
        );


        ProductDocument productDocument = new ProductDocument(
            seller.getId(), productId,
            productName, productDescription,
            price, quantity
        );

        productSearchRepository.save(productDocument);

        return product.getSeller().getId();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SoldProductResponse> getSoldProduct(
        User user, Pageable pageable, LocalDate startDate, LocalDate endDate) {
        Seller seller = findSeller(user);

        return sellerQuery.getSoldProduct(seller, pageable, startDate, endDate);
    }

    @Override
    @Transactional(readOnly = true)
    public SoldProductSumPriceResponse getSoldProductSumPrice(
        User user, LocalDate startDate, LocalDate endDate) {
        Seller seller = findSeller(user);

        return sellerQuery.getSoldProductSumPrice(seller, startDate, endDate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SoldProductQuantityResponse> getSoldProductQuantityTopTen(
        User user, LocalDate startDate, LocalDate endDate) {
        Seller seller = findSeller(user);

        return sellerQuery.getSoldProductQuantityTopTen(seller, startDate, endDate);
    }

    private Seller findSeller(User user) {
        return sellerRepository.findByUserId(user.getId()).orElseThrow(() ->
            new InvalidSellerEventException(INVALID_SELLER_EVENT));
    }

    private Product findProduct(Long productId, Seller seller) {
        return productRepository.findByIdAndSellerId(productId, seller.getId()).orElseThrow(() ->
            new InvalidProductEventException(INVALID_PRODUCT_EVENT));
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

    @Override
    public List<ProductSearchResponse> searchProductName(String keyword) {
        return sellerQuery.searchProductName(keyword);
    }

    @Override
    public List<ProductSearchResponse> searchProductIntroduce(String keyowrd) {
        return sellerQuery.searchProductIntroduce(keyowrd);
    }

    @Override
    @Transactional(readOnly = true)
    public SellerCheckResponse checkSeller(User user) {

        Long sellerId = sellerQuery.checkSeller(user.getId());

        return new SellerCheckResponse(sellerId);
    }

    @Override
    @Transactional
    public Long deleteProduct(Long productId, User user) {
        Seller seller = findSeller(user);

        Product product = findProduct(productId, seller);

        productRepository.deleteById(product.getId());

        productSearchRepository.deleteByProductId(product.getId());

        return product.getSeller().getId();
    }

}
