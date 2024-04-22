package com.kuku9.goods.seller.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.kuku9.goods.common.TestValue;
import com.kuku9.goods.domain.product.entity.Product;
import com.kuku9.goods.domain.product.repository.ProductRepository;
import com.kuku9.goods.domain.search.document.ProductDocument;
import com.kuku9.goods.domain.search.repository.ProductSearchRepository;
import com.kuku9.goods.domain.seller.dto.request.ProductRegistRequest;
import com.kuku9.goods.domain.seller.dto.request.ProductUpdateRequest;
import com.kuku9.goods.domain.seller.dto.response.SoldProductQuantityResponse;
import com.kuku9.goods.domain.seller.dto.response.SoldProductResponse;
import com.kuku9.goods.domain.seller.dto.response.SoldProductSumPriceResponse;
import com.kuku9.goods.domain.seller.entity.Seller;
import com.kuku9.goods.domain.seller.repository.SellerQuery;
import com.kuku9.goods.domain.seller.repository.SellerRepository;
import com.kuku9.goods.domain.seller.service.SellerServiceImpl;
import com.kuku9.goods.domain.user.entity.User;
import com.kuku9.goods.global.exception.InvalidSellerEventException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class SellerServiceImplTest extends TestValue {

    @Mock
    SellerRepository sellerRepository;
    @Mock
    SellerQuery sellerQuery;
    @Mock
    ProductRepository productRepository;
    @Mock
    ProductSearchRepository productSearchRepository;
    @InjectMocks
    private SellerServiceImpl sellerServiceImpl;
    private User user1;
    private User user2;
    private Seller seller;
    private Product product;
    private ProductDocument productDocument;
    private ProductRegistRequest productRegistRequest;
    private ProductUpdateRequest productUpdateRequest;
    private Pageable pageable;
    private LocalDate startDate;
    private LocalDate endDate;
    private Page<SoldProductResponse> soldProductList;
    private SoldProductSumPriceResponse soldProductSumPrice;
    private List<SoldProductQuantityResponse> soldProductQuantity;

    @BeforeEach
    public void UserSetUp() {
        user1 = TEST_USER1;
        user2 = TEST_USER2;
        seller = TEST_SELLER;
        product = TEST_PRODUCT;
        productDocument = TEST_PRODUCT_DOCUMENT;
        productRegistRequest = TEST_PRODUCT_REGIST_REQUEST;
        productUpdateRequest = TEST_PRODUCT_UPDATE_REQUEST;
        pageable = TEST_PAGEABLE;
        startDate = TEST_START_DATE;
        endDate = TEST_END_DATE;
        soldProductList = TEST_SOLD_PRODUCT_LIST;
        soldProductSumPrice = TEST_SOLD_PRODUCT_SUM_PRICE_RESPONSE;
        soldProductQuantity = TEST_SOLD_PRODUCT_QUANTITY_RESPONSE;
    }


    @Nested // 중첩 클래스 정의
    class CreateProductTest {

        @Test
        @DisplayName("상품 등록 성공 - 셀러권한")
        void productRegisterSuccess() {
            // given
            // 입력으로 상품에 대한 정보 입력받기 위해 Request 생성
            given(sellerRepository.findByUserId(anyLong())).willReturn(Optional.of(seller));
            given(productRepository.save(any())).willReturn(product);
            given(productSearchRepository.save(any())).willReturn(productDocument);

            //when
            String domainName = sellerServiceImpl.createProduct(productRegistRequest, user1);

            //then
            verify(productRepository, times(1)).save(any(Product.class));
            verify(productSearchRepository, times(1))
                .save(any(ProductDocument.class));
            assertThat(domainName).isEqualTo(product.getSeller().getDomainName());
        }

        @Test
        @DisplayName("상품 등록 실패 - 일반유저")
        void productRegisterUnsuccess() {
            // given
            given(sellerRepository.findByUserId(anyLong()))
                .willThrow(InvalidSellerEventException.class);

            // when & then
            assertThrows(InvalidSellerEventException.class, () -> {
                sellerServiceImpl.createProduct(productRegistRequest, user2);
            });
        }
    }

    @Nested
    class updateProductStatusTest {

        @Test
        @DisplayName("상품 판매 여부 성공 - 셀러권한")
        void productStatusUpdateSuccess() {
            // given
            given(sellerRepository.findByUserId(anyLong())).willReturn(Optional.of(seller));
            given(productRepository.findByIdAndSellerId(anyLong(), anyLong()))
                .willReturn(Optional.of(product));

            product.updateOrderStatus(product.getStatus());

            // when
            String domainName = sellerServiceImpl.updateProductStatus(product.getId(), user1);

            //then
            assertEquals(domainName, product.getSeller().getDomainName());
        }

        @Test
        @DisplayName("상품 판매 여부 실패 - 일반유저")
        void productStatusUpdateUnsuccess() {
            // given
            given(sellerRepository.findByUserId(anyLong()))
                .willThrow(InvalidSellerEventException.class);

            // when & then
            assertThrows(InvalidSellerEventException.class, () -> {
                sellerServiceImpl.updateProductStatus(product.getId(), user2);
            });
        }
    }

    @Nested
    class ProductUpdateTest {

        @Test
        @DisplayName("상품 정보 수정 성공 - 셀러권한")
        void productUpdateSuccess() {
            // given
            given(sellerRepository.findByUserId(anyLong())).willReturn(Optional.of(seller));
            given(productRepository.findByIdAndSellerId(product.getId(), seller.getId()))
                .willReturn(Optional.of(product));

            product.updateProduct(
                productUpdateRequest.getName(),
                productUpdateRequest.getDescription(),
                productUpdateRequest.getPrice(),
                productUpdateRequest.getQuantity()
            );

            // when
            String domainName = sellerServiceImpl.updateProduct(
                product.getId(),
                productUpdateRequest,
                user1);

            // then
            assertEquals(domainName, product.getSeller().getDomainName());
            assertEquals(productUpdateRequest.getName(), product.getName());
            assertEquals(productUpdateRequest.getDescription(), product.getDescription());
            assertEquals(productUpdateRequest.getPrice(), product.getPrice());
        }

        @Test
        @DisplayName("상품 정보 수정 실패 - 일반유저")
        void productUpdateUnsuccess() {
            // given
            given(sellerRepository.findByUserId(anyLong()))
                .willThrow(InvalidSellerEventException.class);

            // when & then
            assertThrows(InvalidSellerEventException.class, () -> {
                sellerServiceImpl.updateProduct(
                    product.getId(),
                    productUpdateRequest,
                    user2);
            });
        }
    }

    @Nested
    class getSoldProductTest {

        @Test
        @DisplayName("판매된 상품 조회 성공 - 셀러권한")
        void getSoldProductSuccess() {
            // given

            given(sellerRepository.findByUserId(anyLong())).willReturn(Optional.of(seller));
            given(sellerQuery.getSoldProduct(seller, pageable, startDate, endDate))
                .willReturn(soldProductList);

            // when
            Page<SoldProductResponse> responseList = sellerServiceImpl.getSoldProduct(
                user1, pageable, startDate, endDate);

            // then
            assertThat(responseList).isNotNull();
            assertThat(responseList).isNotEmpty();
            for (int i = 0; i < responseList.getSize(); i++) {
                assertEquals(responseList.getContent().get(i).getProductName(),
                    soldProductList.getContent().get(i).getProductName());
                assertEquals(responseList.getContent().get(i).getProductPrice(),
                    soldProductList.getContent().get(i).getProductPrice());
                assertEquals(responseList.getContent().get(i).getProductQuantity(),
                    soldProductList.getContent().get(i).getProductQuantity());
                assertEquals(responseList.getContent().get(i).getOrderProductQuantity(),
                    soldProductList.getContent().get(i).getOrderProductQuantity());
                assertEquals(responseList.getContent().get(i).getProductTotalPrice(),
                    soldProductList.getContent().get(i).getProductTotalPrice());
            }
        }

        @Test
        @DisplayName("판매된 상품 조회 실패 - 일반유저")
        void getSoldProductUnsuccess() {
            // given
            given(sellerRepository.findByUserId(anyLong()))
                .willThrow(InvalidSellerEventException.class);

            // when & then
            assertThrows(InvalidSellerEventException.class, () -> {
                sellerServiceImpl.getSoldProduct(
                    user2,
                    pageable,
                    startDate,
                    endDate);
            });
        }
    }

    @Nested
    class getSoldProductSumPriceTest {

        @Test
        @DisplayName("판매된 상품 총 판매액 조회 성공 - 셀러권한")
        void getSoldProductSumPriceSuccess() {
            // given
            given(sellerRepository.findByUserId(anyLong())).willReturn(Optional.of(seller));
            given(sellerQuery.getSoldProductSumPrice(any(), any(), any()))
                .willReturn(soldProductSumPrice);

            // when
            SoldProductSumPriceResponse totalPrice = sellerServiceImpl.getSoldProductSumPrice(
                user1, startDate, endDate);

            // then
            assertThat(totalPrice).isNotNull();
            assertEquals(totalPrice.getBrandName(), soldProductSumPrice.getBrandName());
            assertEquals(totalPrice.getTotalPrice(), soldProductSumPrice.getTotalPrice());
        }

        @Test
        @DisplayName("판매된 상품 총 판매액 조회 실패 - 일반유저")
        void getSoldProductSumPriceUnsuccess() {
            // given
            given(sellerRepository.findByUserId(anyLong()))
                .willThrow(InvalidSellerEventException.class);

            // when & then
            assertThrows(InvalidSellerEventException.class, () -> {
                sellerServiceImpl.getSoldProductQuantityTopTen(
                    user2,
                    startDate,
                    endDate);
            });
        }
    }

    @Nested
    class getSoldProductQuantityTopTenTest {

        @Test
        @DisplayName("가장 많이 판매된 상품 상위 10개 조회 성공 - 셀러권한")
        void getSoldProductQuantityTopTenSuccess() {
            // given
            given(sellerRepository.findByUserId(anyLong())).willReturn(Optional.of(seller));
            given(sellerQuery.getSoldProductQuantityTopTen(any(), any(), any()))
                .willReturn(soldProductQuantity);

            // when
            List<SoldProductQuantityResponse> topTen =
                sellerServiceImpl.getSoldProductQuantityTopTen(
                    user1,
                    startDate,
                    endDate);

            //then
            assertThat(topTen).isNotNull();
            assertThat(topTen).isNotEmpty();
            for (int i = 0; i < topTen.size(); i++) {
                assertEquals(
                    topTen.get(i).getProductName(),
                    soldProductQuantity.get(i).getProductName());
                assertEquals(
                    topTen.get(i).getProductQuantity(),
                    soldProductQuantity.get(i).getProductQuantity());
            }
        }

        @Test
        @DisplayName("가장 많이 판매된 상품 상위 10개 조회 실패 - 일반유저")
        void getSoldProductQuantityTopTenUnsuccess() {
            // given
            given(sellerRepository.findByUserId(anyLong()))
                .willThrow(InvalidSellerEventException.class);

            // when & then
            assertThrows(InvalidSellerEventException.class, () -> {
                sellerServiceImpl.getSoldProductQuantityTopTen(
                    user2,
                    startDate,
                    endDate);
            });
        }
    }

    @Nested
    class searchBrandTest {

    }

    @Nested
    class saveSellerTest {

        @Test
        @DisplayName("셀러 등록 성공")
        void saveSellerSuccess() {
            // given
            given(sellerRepository.save(any())).willReturn(seller);

            // when
            Seller saveSeller = sellerServiceImpl.save(seller);

            // then
            assertThat(saveSeller).isNotNull();
        }
    }
}
