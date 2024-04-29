package com.kuku9.goods.seller.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.kuku9.goods.common.TestValue;
import com.kuku9.goods.domain.product.entity.Product;
import com.kuku9.goods.domain.product.repository.ProductRepository;
import com.kuku9.goods.domain.search.document.ProductDocument;
import com.kuku9.goods.domain.search.dto.ProductSearchResponse;
import com.kuku9.goods.domain.search.dto.SellerSearchResponse;
import com.kuku9.goods.domain.search.repository.ProductSearchRepository;
import com.kuku9.goods.domain.seller.dto.request.ProductRegistRequest;
import com.kuku9.goods.domain.seller.dto.request.ProductUpdateRequest;
import com.kuku9.goods.domain.seller.dto.response.*;
import com.kuku9.goods.domain.seller.entity.Seller;
import com.kuku9.goods.domain.seller.repository.SellerQuery;
import com.kuku9.goods.domain.seller.repository.SellerRepository;
import com.kuku9.goods.domain.seller.service.SellerServiceImpl;
import com.kuku9.goods.domain.user.entity.User;
import com.kuku9.goods.global.exception.DuplicatedException;
import com.kuku9.goods.global.exception.InvalidProductEventException;
import com.kuku9.goods.global.exception.InvalidSellerEventException;
import com.kuku9.goods.global.exception.NotFoundException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SellerServiceImplTest extends TestValue {

    @Mock
    private SellerRepository sellerRepository;
    @Mock
    private SellerQuery sellerQuery;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductSearchRepository productSearchRepository;
    @InjectMocks
    private SellerServiceImpl sellerServiceImpl;
    private User user1;
    private User user2;
    private Seller seller;
    private Product product;
    private Product updateProduct;
    private ProductDocument productDocument;
    private ProductRegistRequest productRegistRequest;
    private ProductUpdateRequest productUpdateRequest;
    private Pageable pageable;
    private LocalDate startDate;
    private LocalDate endDate;
    private Page<SoldProductResponse> soldProductList;
    private SoldProductSumPriceResponse soldProductSumPrice;
    private List<SoldProductQuantityResponse> soldProductQuantity;
    private List<ProductSearchResponse> productSearchResponseList;
    private List<SellerSearchResponse> sellerSearchResponseList;

    @BeforeEach
    void SetUp() {
        user1 = TEST_USER1;
        user2 = TEST_USER2;
        seller = TEST_SELLER;
        product = TEST_PRODUCT;
        updateProduct = TEST_UPDATE_PRODUCT;
        productDocument = TEST_PRODUCT_DOCUMENT;
        productRegistRequest = TEST_PRODUCT_REGIST_REQUEST;
        productUpdateRequest = TEST_PRODUCT_UPDATE_REQUEST;
        pageable = TEST_PAGEABLE;
        startDate = TEST_START_DATE;
        endDate = TEST_END_DATE;
        soldProductList = TEST_SOLD_PRODUCT_LIST;
        soldProductSumPrice = TEST_SOLD_PRODUCT_SUM_PRICE_RESPONSE;
        soldProductQuantity = TEST_SOLD_PRODUCT_QUANTITY_RESPONSE;
        productSearchResponseList = TEST_SEARCH_PRODUCT_RESPONSE;
        sellerSearchResponseList = TEST_SEARCH_SELLER_RESPONSE;
    }


    @Nested // 중첩 클래스 정의
    @Order(1)
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
        void productRegisterFailure() {
            // given
            given(sellerRepository.findByUserId(anyLong())).willReturn(Optional.empty());

            // when & then
            assertThrows(InvalidSellerEventException.class, () -> {
                sellerServiceImpl.createProduct(productRegistRequest, user2);
            });
        }
    }

    @Nested
    @Order(2)
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
            verify(sellerRepository, times(1)).findByUserId(anyLong());
            verify(productRepository, times(1))
                .findByIdAndSellerId(anyLong(), anyLong());
            assertEquals(domainName, product.getSeller().getDomainName());
        }

        @Test
        @DisplayName("상품 판매 여부 실패 - 일반유저")
        void productStatusUpdateFailure() {
            // given
            given(sellerRepository.findByUserId(anyLong())).willReturn(Optional.empty());

            // when & then
            assertThrows(InvalidSellerEventException.class, () -> {
                sellerServiceImpl.updateProductStatus(product.getId(), user2);
            });
        }
    }

    @Nested
    @Order(3)
    class updateProductTest {

        @Test
        @DisplayName("상품 정보 수정 성공 - 모든 필드 업데이트")
        void updateProductSuccess() {
            // given
            given(sellerRepository.findByUserId(anyLong())).willReturn(Optional.of(seller));
            given(productRepository.findByIdAndSellerId(anyLong(), anyLong()))
                .willReturn(Optional.of(updateProduct));


            updateProduct.updateProduct(
                productUpdateRequest.getName(),
                productUpdateRequest.getDescription(),
                productUpdateRequest.getPrice(),
                productUpdateRequest.getQuantity()
            );

            // when
            String domainName = sellerServiceImpl.updateProduct(
                updateProduct.getId(),
                productUpdateRequest,
                user1);

            // then
            verify(sellerRepository, times(1)).findByUserId(anyLong());
            verify(productRepository, times(1))
                .findByIdAndSellerId(anyLong(), anyLong());
            assertEquals(domainName, updateProduct.getSeller().getDomainName());
            assertEquals(productUpdateRequest.getName(), updateProduct.getName());
            assertEquals(productUpdateRequest.getDescription(), updateProduct.getDescription());
            assertEquals(productUpdateRequest.getPrice(), updateProduct.getPrice());
        }

        @Test
        @DisplayName("상품 수정 성공 - 부분 필드 업데이트")
        void updateProductPartialInfo() {
            // given
            given(sellerRepository.findByUserId(anyLong())).willReturn(Optional.of(seller));
            given(productRepository.findByIdAndSellerId(anyLong(), anyLong()))
                .willReturn(Optional.of(updateProduct));

            // when
            String domainName = sellerServiceImpl.updateProduct(
                updateProduct.getId(),
                new ProductUpdateRequest("", "", 0, 0),
                user1);

            // then
            verify(productRepository, times(1))
                .findByIdAndSellerId(anyLong(), anyLong());
            verify(productSearchRepository, times(1))
                .deleteByProductId(anyLong());
            verify(productSearchRepository, times(1))
                .save(any(ProductDocument.class));
            assertEquals(TEST_UPDATE_PRODUCT_NAME, updateProduct.getName());
            assertEquals(TEST_UPDATE_PRODUCT_DESCRIPTION, updateProduct.getDescription());
            assertEquals(TEST_UPDATE_PRODUCT_PRICE, updateProduct.getPrice());
            assertEquals(TEST_UPDATE_PRODUCT_QUANTITY, updateProduct.getQuantity());
            assertEquals(domainName, updateProduct.getSeller().getDomainName());
        }

        @Test
        @DisplayName("상품 정보 수정 실패 - 일반유저")
        void updateProductFailure() {
            // given
            given(sellerRepository.findByUserId(anyLong())).willReturn(Optional.empty());

            // when & then
            assertThrows(InvalidSellerEventException.class, () -> {
                sellerServiceImpl.updateProduct(
                    updateProduct.getId(),
                    productUpdateRequest,
                    user2);
            });
        }

        @Test
        @DisplayName("상품 정보 수정 실패 - request null")
        void updateProductNull() {
            // given
            given(sellerRepository.findByUserId(anyLong())).willReturn(Optional.ofNullable(seller));
            given(productRepository.findByIdAndSellerId(anyLong(), anyLong()))
                .willReturn(Optional.ofNullable(updateProduct));

            // when
            String domainName = sellerServiceImpl
                .updateProduct(updateProduct.getId(), null, user1);

            // then
            assertEquals(updateProduct.getSeller().getDomainName(), domainName);
        }
    }

    @Nested
    @Order(4)
    class deleteProductTest {
        @Test
        @DisplayName("상품 삭제 성공")
        void deleteProductSuccess() {
            // given
            given(sellerRepository.findByUserId(anyLong())).willReturn(Optional.of(seller));
            given(productRepository.findByIdAndSellerId(anyLong(), anyLong()))
                .willReturn(Optional.ofNullable(product));

            // when
            String domainName = sellerServiceImpl.deleteProduct(product.getId(), user1);

            // then
            verify(sellerRepository, times(1)).findByUserId(anyLong());
            verify(productRepository, times(1))
                .findByIdAndSellerId(anyLong(), any());
            assertEquals(product.getSeller().getDomainName(), domainName);
        }

        @Test
        @DisplayName("상품 삭제 실패")
        void deleteProductFailure() {
            // given
            given(sellerRepository.findByUserId(anyLong())).willReturn(Optional.empty());

            // when & then
            assertThrows(InvalidSellerEventException.class, () -> {
                sellerServiceImpl.deleteProduct(product.getId(), user1);
            });
        }

        @Test
        @DisplayName("상품 삭제 실패 - 상품 정보 없음")
        void deleteProductNull() {
            // given
            given(sellerRepository.findByUserId(anyLong())).willReturn(Optional.of(seller));
            given(productRepository.findByIdAndSellerId(anyLong(), anyLong()))
                .willReturn(Optional.empty());

            // when & then
            assertThrows(InvalidProductEventException.class, () -> {
                sellerServiceImpl.deleteProduct(product.getId(), user1);
            });
        }
    }

    @Nested
    @Order(5)
    class getSoldProductTest {

        @Test
        @DisplayName("판매된 상품 조회 성공 - 셀러권한")
        void getSoldProductSuccess() {
            // given

            given(sellerRepository.findByUserId(anyLong())).willReturn(Optional.of(seller));
            given(sellerQuery.getSoldProduct(any(), any(), any(), any()))
                .willReturn(soldProductList);

            // when
            Page<SoldProductResponse> responseList = sellerServiceImpl.getSoldProduct(
                user1, pageable, startDate, endDate);

            // then
            verify(sellerRepository, times(1)).findByUserId(anyLong());
            verify(sellerQuery, times(1))
                .getSoldProduct(any(), any(), any(), any());
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
        void getSoldProductFailure() {
            // given
            given(sellerRepository.findByUserId(anyLong())).willReturn(Optional.empty());

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
    @Order(6)
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
            verify(sellerRepository, times(1)).findByUserId(anyLong());
            verify(sellerQuery, times(1))
                .getSoldProductSumPrice(any(), any(), any());
            assertThat(totalPrice).isNotNull();
            assertEquals(totalPrice.getBrandName(), soldProductSumPrice.getBrandName());
            assertEquals(totalPrice.getTotalPrice(), soldProductSumPrice.getTotalPrice());
        }

        @Test
        @DisplayName("판매된 상품 총 판매액 조회 실패 - 일반유저")
        void getSoldProductSumPriceFailure() {
            // given
            given(sellerRepository.findByUserId(anyLong())).willReturn(Optional.empty());

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
    @Order(7)
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
            verify(sellerRepository, times(1)).findByUserId(anyLong());
            verify(sellerQuery, times(1))
                .getSoldProductQuantityTopTen(any(), any(), any());
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
        void getSoldProductQuantityTopTenFailure() {
            // given
            given(sellerRepository.findByUserId(anyLong())).willReturn(Optional.empty());

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
    @Order(8)
    class saveSellerTest {

        @Test
        @DisplayName("셀러 등록 성공")
        void saveSellerSuccess() {
            // given
            given(sellerRepository.save(any())).willReturn(seller);

            // when
            Seller saveSeller = sellerServiceImpl.save(seller);

            // then
            verify(sellerRepository, times(1)).save(any());
            assertThat(saveSeller).isNotNull();
        }

        @Test
        @DisplayName("셀러 등록 실패")
        void saveSellerFailure() {
            // given
            given(sellerRepository.save(any())).willThrow(InvalidSellerEventException.class);

            // when & then
            assertThrows(InvalidSellerEventException.class, () -> {
                sellerServiceImpl.save(seller);
            });
        }
    }

    @Nested
    @Order(9)
    class checkSellerExistsByUserIdTest {

        @Test
        @DisplayName("셀러 등록을 위한 데이터 중복 체크 성공")
        void checkSellerExistsByDataSuccess() {
            // given
            given(sellerRepository.existsByUserId(anyLong())).willReturn(true);
            given(sellerRepository.existsByBrandName(anyString())).willReturn(true);
            given(sellerRepository.existsByDomainName(anyString())).willReturn(true);
            given(sellerRepository.existsByEmail(anyString())).willReturn(true);
            given(sellerRepository.existsByPhoneNumber(anyString())).willReturn(true);

            // when
            Boolean boolUserId = sellerServiceImpl.checkSellerExistsByUserId(user1.getId());
            Boolean boolBrandName = sellerServiceImpl.checkBrandNameExist(seller.getBrandName());
            Boolean boolDomainName = sellerServiceImpl.checkDomainNameExist(seller.getDomainName());
            Boolean boolEmail = sellerServiceImpl.checkEmailExist(seller.getEmail());
            Boolean boolPhoneNumber = sellerServiceImpl.checkPhoneNumberExist(seller.getPhoneNumber());

            // then
            verify(sellerRepository, times(1)).existsByUserId(user1.getId());
            verify(sellerRepository, times(1)).existsByBrandName(seller.getBrandName());
            verify(sellerRepository, times(1)).existsByDomainName(seller.getDomainName());
            verify(sellerRepository, times(1)).existsByEmail(seller.getEmail());
            verify(sellerRepository, times(1)).existsByPhoneNumber(seller.getPhoneNumber());

            assertThat(boolUserId).isNotNull().isEqualTo(true);
            assertThat(boolBrandName).isNotNull().isEqualTo(true);
            assertThat(boolDomainName).isNotNull().isEqualTo(true);
            assertThat(boolEmail).isNotNull().isEqualTo(true);
            assertThat(boolPhoneNumber).isNotNull().isEqualTo(true);
        }

        @Test
        @DisplayName("셀러 등록을 위한 데이터 중복 체크 실패")
        void checkSellerExistsByDataFailure() {
            // given
            given(sellerRepository.existsByUserId(anyLong()))
                .willThrow(DuplicatedException.class);
            given(sellerRepository.existsByBrandName(anyString()))
                .willThrow(DuplicatedException.class);
            given(sellerRepository.existsByDomainName(anyString()))
                .willThrow(DuplicatedException.class);
            given(sellerRepository.existsByEmail(anyString()))
                .willThrow(DuplicatedException.class);
            given(sellerRepository.existsByPhoneNumber(anyString()))
                .willThrow(DuplicatedException.class);

            // when & then
            assertThrows(DuplicatedException.class, () -> {
                sellerServiceImpl.checkSellerExistsByUserId(user2.getId());
            });
            assertThrows(DuplicatedException.class, () -> {
                sellerServiceImpl.checkBrandNameExist(seller.getBrandName());
            });
            assertThrows(DuplicatedException.class, () -> {
                sellerServiceImpl.checkDomainNameExist(seller.getDomainName());
            });
            assertThrows(DuplicatedException.class, () -> {
                sellerServiceImpl.checkEmailExist(seller.getEmail());
            });
            assertThrows(DuplicatedException.class, () -> {
                sellerServiceImpl.checkPhoneNumberExist(seller.getEmail());
            });
        }
    }

    @Nested
    @Order(10)
    class searchProductTest {

        @Test
        @DisplayName("상품 검색 성공")
        void searchProductSuccess() {
            // given
            given(sellerQuery.searchProduct(anyString())).willReturn(productSearchResponseList);

            // when
            List<ProductSearchResponse> responseList =
                sellerServiceImpl.searchProduct(anyString());

            // then
            verify(sellerQuery, times(1)).searchProduct(anyString());
            assertThat(responseList).isNotEmpty();
            assertThat(responseList).isNotNull();
            assertEquals(responseList.get(0).getProductName(), "test product 1");
            assertEquals(responseList.get(1).getProductName(), "test product 2");
            assertEquals(responseList.get(0).getIntroduce(), "test description 1");
            assertEquals(responseList.get(1).getIntroduce(), "test description 2");
        }

        @Test
        @DisplayName("상품 검색 실패")
        void searchProductEmpty() {
            // given
            given(sellerQuery.searchProduct(anyString())).willReturn(Collections.emptyList());

            // when & then
            assertThrows(NotFoundException.class, () -> {
                sellerServiceImpl.searchProduct(anyString());
            });
        }
    }

    @Nested
    @Order(11)
    class searchBrandTest {

        @Test
        @DisplayName("브랜드 검색 성공")
        void searchBrandSuccess() {
            // given
            given(sellerQuery.searchBrand(anyString()))
                .willReturn(sellerSearchResponseList);

            // when
            List<SellerSearchResponse> responseList =
                sellerServiceImpl.searchBrand(anyString());

            // then
            verify(sellerQuery, times(1)).searchBrand(anyString());
            assertThat(responseList).isNotEmpty();
            assertThat(responseList).isNotNull();
            assertEquals(responseList.get(0).getBrandName(), "test brand 1");
            assertEquals(responseList.get(1).getBrandName(), "test brand 2");
            assertEquals(responseList.get(0).getIntroduce(), "test introduce 1");
            assertEquals(responseList.get(1).getIntroduce(), "test introduce 2");
        }

        @Test
        @DisplayName("브랜드 검색 실패")
        void searchBrandEmpty() {
            // given
            given(sellerQuery.searchBrand(anyString())).willReturn(Collections.emptyList());

            //when & then
            assertThrows(NotFoundException.class, () -> {
                sellerServiceImpl.searchBrand(anyString());
            });
        }

    }

}
