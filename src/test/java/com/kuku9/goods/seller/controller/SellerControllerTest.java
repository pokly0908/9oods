package com.kuku9.goods.seller.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kuku9.goods.common.TestValue;
import com.kuku9.goods.domain.search.dto.ProductSearchResponse;
import com.kuku9.goods.domain.search.dto.SellerSearchResponse;
import com.kuku9.goods.domain.seller.controller.SellerController;
import com.kuku9.goods.domain.seller.dto.request.ProductRegistRequest;
import com.kuku9.goods.domain.seller.dto.request.ProductUpdateRequest;
import com.kuku9.goods.domain.seller.dto.response.SoldProductQuantityResponse;
import com.kuku9.goods.domain.seller.dto.response.SoldProductResponse;
import com.kuku9.goods.domain.seller.dto.response.SoldProductSumPriceResponse;
import com.kuku9.goods.domain.seller.entity.Seller;
import com.kuku9.goods.domain.seller.service.SellerService;
import com.kuku9.goods.global.exception.ExceptionStatus;
import com.kuku9.goods.global.exception.InvalidSellerEventException;
import com.kuku9.goods.global.exception.NotFoundException;
import com.kuku9.goods.global.security.CustomUserDetails;
import java.time.LocalDate;
import java.util.List;
import jdk.jshell.spi.ExecutionControlProvider;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;

@WebMvcTest(SellerController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SellerControllerTest extends TestValue {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SellerService sellerService;

    @Autowired
    private ObjectMapper objectMapper;

    private String domainName;
    private Long productId;
    private CustomUserDetails user1;
    private CustomUserDetails user2;
    private Seller seller;
    private ProductRegistRequest productRegistRequest;
    private ProductUpdateRequest productUpdateRequest;
    private int page;
    private int size;
    private LocalDate startDate;
    private LocalDate endDate;
    private Page<SoldProductResponse> soldProductResponseList;
    private SoldProductSumPriceResponse soldProductSumPriceResponse;
    private List<SoldProductQuantityResponse> soldProductQuantityResponse;
    private List<ProductSearchResponse> productSearchResponseList;
    private List<SellerSearchResponse> sellerSearchResponseList;

    @BeforeEach
    public void setUp() {
        domainName = TEST_SELLER_BRANDNAME1;
        productId = TEST_PRODUCT_ID1;
        user1 = new CustomUserDetails(TEST_USER1);
        user2 = new CustomUserDetails(TEST_USER2);
        seller = TEST_SELLER;
        productRegistRequest = TEST_PRODUCT_REGIST_REQUEST;
        productUpdateRequest = TEST_PRODUCT_UPDATE_REQUEST;
        page = TEST_PAGE;
        size = TEST_SIZE;
        startDate = TEST_START_DATE;
        endDate = TEST_END_DATE;
        soldProductResponseList = TEST_SOLD_PRODUCT_LIST;
        soldProductSumPriceResponse = TEST_SOLD_PRODUCT_SUM_PRICE_RESPONSE;
        soldProductQuantityResponse = TEST_SOLD_PRODUCT_QUANTITY_RESPONSE;
        productSearchResponseList = TEST_SEARCH_PRODUCT_RESPONSE;
        sellerSearchResponseList = TEST_SEARCH_SELLER_RESPONSE;
    }

    @Nested
    class createProductTest {
        @Test
        @Order(1)
        @DisplayName("상품 등록 성공 - 셀러권한")
        void productRegisterSuccess() throws Exception {
            // given
            given(sellerService.createProduct(any(), any())).willReturn(domainName);

            // when then
            mockMvc.perform(post("/api/v1/sellers/products")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(productRegistRequest))
                    .with(SecurityMockMvcRequestPostProcessors.user(user1))
                    .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isCreated())
                .andExpect(header().string(
                    "Location", "/api/v1/seller/" + domainName + "/products"));

            verify(sellerService, times(1))
                .createProduct(any(), any());
        }

        @Test
        @Order(2)
        @DisplayName("상품 등록 실패 - 일반유저")
        void productRegisterFailure() throws Exception {
            // given
            given(sellerService.createProduct(any(), any()))
                .willThrow(new InvalidSellerEventException(ExceptionStatus.INVALID_SELLER_EVENT));

            // when & then
            mockMvc.perform(post("/api/v1/sellers/products")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(productRegistRequest))
                    .with(SecurityMockMvcRequestPostProcessors.user(user2))
                    .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isBadRequest());
        }
    }

    @Nested
    class updateProductStatusTest {

        @Test
        @Order(1)
        @DisplayName("상품 판매 여부 수정 성공 - 셀러권한")
        void updateProductStatusSuccess() throws Exception {
            // given
            given(sellerService.updateProductStatus(anyLong(), any())).willReturn(domainName);

            // when & then
            mockMvc.perform(
                patch("/api/v1/sellers/products/{productId}/status", productId)
                    .with(SecurityMockMvcRequestPostProcessors.user(user1))
                    .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isCreated())
                .andExpect(header().string(
                    "Location", "/api/v1/seller/" + domainName + "/products"));

            verify(sellerService, times(1))
                .updateProductStatus(anyLong(), any());
        }

        @Test
        @Order(2)
        @DisplayName("상품 판매 여부 수정 실패 - 일반유저")
        void updateProductStatusFailure() throws Exception {
            // given
            given(sellerService.updateProductStatus(anyLong(), any()))
                .willThrow(new InvalidSellerEventException(ExceptionStatus.INVALID_SELLER_EVENT));

            // when & then
            mockMvc.perform(
                patch("/api/v1/sellers/products/{productId}/status", productId)
                    .with(SecurityMockMvcRequestPostProcessors.user(user1))
                    .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isBadRequest());
        }
    }

    @Nested
    class updateProductTest {

        @Test
        @Order(1)
        @DisplayName("상품 정보 수정 성공 - 셀러권한")
        void updateProductSuccess() throws Exception {
            // given
            given(sellerService.updateProduct(anyLong(), any(), any())).willReturn(domainName);

            // when & then
            mockMvc.perform(patch("/api/v1/sellers/products/{productId}", productId)
                    .contentType(APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(productUpdateRequest))
                .with(SecurityMockMvcRequestPostProcessors.user(user1))
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isCreated())
                .andExpect(header().string(
                    "Location", "/api/v1/seller/" + domainName + "/products"));

            verify(sellerService, times(1))
                .updateProduct(anyLong(), any(), any());
        }

        @Test
        @Order(2)
        @DisplayName("상품 정보 수정 실패 - 일반유저")
        void updateProductFailure() throws Exception{
            // given
            given(sellerService.updateProduct(anyLong(), any(), any()))
                .willThrow(new InvalidSellerEventException(ExceptionStatus.INVALID_SELLER_EVENT));

            // when & then
            mockMvc.perform(patch("/api/v1/sellers/products/{productId}", productId)
                    .contentType(APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(productUpdateRequest))
                .with(SecurityMockMvcRequestPostProcessors.user(user2))
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isBadRequest());
        }
    }

    @Nested
    class deleteProductTest {

        @Test
        @Order(1)
        @DisplayName("상품 삭제 성공 - 셀러권한")
        void deleteProductSuccess() throws Exception {
            // given
            given(sellerService.deleteProduct(anyLong(), any())).willReturn(domainName);

            // when then
            mockMvc.perform(delete("/api/v1/sellers/products/{productId}", productId)
                .with(SecurityMockMvcRequestPostProcessors.user(user1))
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isCreated())
                .andExpect(header().string(
                    "Location", "/api/v1/seller/" + domainName + "/products"));

            verify(sellerService, times(1)).deleteProduct(anyLong(), any());
        }

        @Test
        @Order(2)
        @DisplayName("상품 삭제 실패 - 일반유저")
        void deleteProductFailure() throws Exception {
            // given
            given(sellerService.deleteProduct(anyLong(), any()))
                .willThrow(new InvalidSellerEventException(ExceptionStatus.INVALID_SELLER_EVENT));

            // when & then
            mockMvc.perform(delete("/api/v1/sellers/products/{productId}", productId)
                .with(SecurityMockMvcRequestPostProcessors.user(user2))
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isBadRequest());
        }
    }

    @Nested
    class getSoldProductTest {

        @Test
        @Order(1)
        @DisplayName("판매된 상품 조회 성공 - 셀러권한")
        void getSoldProductSuccess() throws Exception {
            // given
            given(sellerService.getSoldProduct(any(), any(), any(), any()))
                .willReturn(soldProductResponseList);

            // when & then
            mockMvc.perform(get("/api/v1/sellers/products/sold")
                .param("page", String.valueOf(page))
                .param("size", String.valueOf(size))
                .param("startDate", startDate.toString())
                .param("endDate", endDate.toString())
                .with(SecurityMockMvcRequestPostProcessors.user(user1))
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk());

            verify(sellerService, times(1))
                .getSoldProduct(any(), any(), any(), any());
        }

        @Test
        @Order(2)
        @DisplayName("판매된 상품 조회 실패 - 일반유저")
        void getSoldProductFailure() throws Exception {
            // given
            given(sellerService.getSoldProduct(any(), any(), any(), any()))
                .willThrow(new InvalidSellerEventException(ExceptionStatus.INVALID_SELLER_EVENT));

            // when & then
            mockMvc.perform(get("/api/v1/sellers/products/sold")
                .param("page", String.valueOf(page))
                .param("size", String.valueOf(size))
                .param("startDate", startDate.toString())
                .param("endDate", endDate.toString())
                .with(SecurityMockMvcRequestPostProcessors.user(user2))
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isBadRequest());
        }
    }

    @Nested
    class getSoldProductSumPriceTest {

        @Test
        @Order(1)
        @DisplayName("판매 총 금액 조회 성공 - 셀러권한")
        void getSoldProductSumPriceSuccess() throws Exception {
            // given
            given(sellerService.getSoldProductSumPrice(any(), any(), any()))
                .willReturn(soldProductSumPriceResponse);

            // when & then
            mockMvc.perform(get("/api/v1/sellers/products/sold/price")
                .param("startDate", startDate.toString())
                .param("endDate", endDate.toString())
                .with(SecurityMockMvcRequestPostProcessors.user(user1))
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk());

            verify(sellerService, times(1))
                .getSoldProductSumPrice(any(), any(), any());
        }

        @Test
        @Order(2)
        @DisplayName("판매 총 금액 조회 실패 - 일반유저")
        void getSoldProductSumPriceFailure() throws Exception {
            // given
            given(sellerService.getSoldProductSumPrice(any(), any(), any()))
                .willThrow(new InvalidSellerEventException(ExceptionStatus.INVALID_SELLER_EVENT));

            // when & then
            mockMvc.perform(get("/api/v1/sellers/products/sold/price")
                .param("startDate", startDate.toString())
                .param("endDate", endDate.toString())
                .with(SecurityMockMvcRequestPostProcessors.user(user2))
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isBadRequest());
        }
    }

    @Nested
    class getSoldProductQuantityTopTen {

        @Test
        @Order(1)
        @DisplayName("판매된 상품 상위 10개 조회 성공 - 셀러권한")
        void getSoldProductQuantityTopTenSuccess() throws Exception {
            // given
            given(sellerService.getSoldProductQuantityTopTen(any(), any(), any()))
                .willReturn(soldProductQuantityResponse);

            // when & then
            mockMvc.perform(get("/api/v1/sellers/products/sold/topten")
                .param("startDate", startDate.toString())
                .param("endDate", endDate.toString())
                .with(SecurityMockMvcRequestPostProcessors.user(user1))
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk());

            verify(sellerService, times(1))
                .getSoldProductQuantityTopTen(any(), any(), any());
        }

        @Test
        @Order(2)
        @DisplayName("판매된 상품 상위 10개 조회 실패 - 일반유저")
        void getSoldProductQuantityTopTenFailure() throws Exception {
            // given
            given(sellerService.getSoldProductQuantityTopTen(any(), any(), any()))
                .willThrow(new InvalidSellerEventException(ExceptionStatus.INVALID_SELLER_EVENT));

            // when & then
            mockMvc.perform(get("/api/v1/sellers/products/sold/topten")
                .param("startDate", startDate.toString())
                .param("endDate", endDate.toString())
                .with(SecurityMockMvcRequestPostProcessors.user(user2))
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isBadRequest());
        }
    }

    @Nested
    class searchProductTest {

        @Test
        @Order(1)
        @DisplayName("상품 검색 조회 성공")
        void searchProductSuccess() throws Exception {
            // given
            given(sellerService.searchProduct(anyString())).willReturn(productSearchResponseList);

            // when & then
            mockMvc.perform(get("/api/v1/sellers/products")
                .param("keyword", "test")
                .with(SecurityMockMvcRequestPostProcessors.user(user1))
                .with(SecurityMockMvcRequestPostProcessors.user(user2))
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk());

            verify(sellerService, times(1)).searchProduct(anyString());
        }

        @Test
        @Order(2)
        @DisplayName("상품 검색 조회 실패")
        void searchProductFailure() throws Exception {
            // given
            given(sellerService.searchProduct(anyString()))
                .willThrow(new NotFoundException(ExceptionStatus.NOT_FOUND_SEARCH_KEYWORD));

            // when & then
            mockMvc.perform(get("/api/v1/sellers/products")
                .param("keyword", "test")
                .with(SecurityMockMvcRequestPostProcessors.user(user1))
                .with(SecurityMockMvcRequestPostProcessors.user(user2))
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isBadRequest());
        }
    }

    @Nested
    class searchBrandTest {

        @Test
        @Order(1)
        @DisplayName("브랜드 검색 조회 성공")
        void searchBrandSuccess() throws Exception {
            // given
            given(sellerService.searchBrand(anyString())).willReturn(sellerSearchResponseList);

            // when & then
            mockMvc.perform(get("/api/v1/sellers/brands")
                .param("keyword", "test")
                .with(SecurityMockMvcRequestPostProcessors.user(user1))
                .with(SecurityMockMvcRequestPostProcessors.user(user2))
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk());

            verify(sellerService, times(1)).searchBrand(anyString());
        }

        @Test
        @Order(2)
        @DisplayName("브랜드 검색 조회 실패")
        void searchBrandFailure() throws Exception {
            // given
            given(sellerService.searchBrand(anyString()))
                .willThrow(new NotFoundException(ExceptionStatus.NOT_FOUND_SEARCH_KEYWORD));

            // when & then
            mockMvc.perform(get("/api/v1/sellers/brands")
                .param("keyword", "test")
                .with(SecurityMockMvcRequestPostProcessors.user(user1))
                .with(SecurityMockMvcRequestPostProcessors.user(user2))
                .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isBadRequest());

        }
    }
}
