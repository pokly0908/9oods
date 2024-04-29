package com.kuku9.goods.seller.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.OPTIONAL_INT;
import static org.assertj.core.api.InstanceOfAssertFactories.optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.kuku9.goods.common.TestValue;
import com.kuku9.goods.domain.order_product.entity.QOrderProduct;
import com.kuku9.goods.domain.product.entity.QProduct;
import com.kuku9.goods.domain.seller.dto.response.SoldProductResponse;
import com.kuku9.goods.domain.seller.entity.QSeller;
import com.kuku9.goods.domain.seller.entity.Seller;
import com.kuku9.goods.domain.seller.repository.SellerQueryImpl;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAQueryBase;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import net.bytebuddy.NamingStrategy;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SellerQueryImplTest extends TestValue {

    @Mock
    private JPAQueryFactory jpaQueryFactory;
    @Mock
    private QSeller qSeller;
    @Mock
    private QProduct qProduct;
    @Mock
    private QOrderProduct qOrderProduct;
    @InjectMocks
    private SellerQueryImpl sellerQueryImpl;

    private Seller seller;
    private LocalDate startDate;
    private LocalDate endDate;
    private Page<SoldProductResponse> soldProductResponseList;
    private Pageable pageable;

    @BeforeEach
    void SetUp() {
        seller = TEST_SELLER;
        startDate = TEST_START_DATE;
        endDate = TEST_END_DATE;
        soldProductResponseList = TEST_SOLD_PRODUCT_LIST;
        pageable = TEST_PAGEABLE;
    }

    @Nested
    class getSoldProductTest {

        @Test
        @DisplayName("판매된 상품 조회")
        void getSoldProductSuccess() {
            // given
            given(jpaQueryFactory.select(Projections.constructor(
                SoldProductResponse.class,
                qProduct.name,
                qProduct.price,
                qProduct.quantity,
                qOrderProduct.quantity,
                qOrderProduct.quantity.multiply(qProduct.price),
                qOrderProduct.createdAt))
                .from(qSeller)
                .join(qProduct).on(qSeller.id.eq(qProduct.seller.id))
                .join(qOrderProduct).on(qProduct.id.eq(qOrderProduct.product.id))
                .where(qSeller.eq(seller)
                    .and(qOrderProduct.createdAt.between(
                        startDate.atStartOfDay(), endDate.plusDays(1).atStartOfDay())))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch()
            ).willReturn(jpaQueryFactory.select(Projections.constructor(
                SoldProductResponse.class,
                qProduct.name,
                qProduct.price,
                qProduct.quantity,
                qOrderProduct.quantity,
                qOrderProduct.quantity.multiply(qProduct.price),
                qOrderProduct.createdAt))
                .from(qSeller)
                .join(qProduct).on(qSeller.id.eq(qProduct.seller.id))
                .join(qOrderProduct).on(qProduct.id.eq(qOrderProduct.product.id))
                .where(qSeller.eq(seller)
                    .and(qOrderProduct.createdAt.between(
                        startDate.atStartOfDay(), endDate.plusDays(1).atStartOfDay())))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch());

            given(jpaQueryFactory
                .selectFrom(qOrderProduct)
                .where(qOrderProduct.createdAt.between(
                    startDate.atStartOfDay(), endDate.plusDays(1).atStartOfDay()))
                .fetchCount())
                .willReturn(jpaQueryFactory
                    .selectFrom(qOrderProduct)
                    .where(qOrderProduct.createdAt.between(
                        startDate.atStartOfDay(), endDate.plusDays(1).atStartOfDay()))
                    .fetchCount());

            // when
            Page<SoldProductResponse> responseList = sellerQueryImpl.getSoldProduct(
                seller, pageable, startDate, endDate);

            // then
            assertThat(responseList).isNotEmpty();
            assertThat(responseList).isNotNull();
        }
    }

}
