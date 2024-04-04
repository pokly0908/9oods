package com.kuku9.goods.seller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.kuku9.goods.common.TestValue;
import com.kuku9.goods.domain.product.entity.Product;
import com.kuku9.goods.domain.product.repository.ProductRepository;
import com.kuku9.goods.domain.seller.dto.request.ProductRegistRequest;
import com.kuku9.goods.domain.seller.entity.Seller;
import com.kuku9.goods.domain.seller.repository.SellerRepository;
import com.kuku9.goods.domain.seller.service.SellerServiceImpl;
import com.kuku9.goods.domain.user.entity.User;
import com.kuku9.goods.global.exception.InvalidSellerEventException;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SellerServiceImplTest extends TestValue {

    @Mock
    SellerRepository sellerRepository;
    @Mock
    ProductRepository productRepository;
    @InjectMocks
    private SellerServiceImpl sellerServiceImpl;

    @Nested // 중첩 클래스 정의
    class CreateProductTest {

        @Test
        @DisplayName("상품 등록 성공 - 셀러권한")
        void productRegisterSuccess() {
            // given
            // 입력으로 상품에 대한 정보 입력받기 위해 Request 생성
            ProductRegistRequest request = TEST_PRODUCT_REGIST_REQUEST;
            Seller seller = TEST_SELLER;
            User user = TEST_SELLER_USER;
            Product product = TEST_PRODUCT;

            given(sellerRepository.findByUserId(anyLong())).willReturn(Optional.of(seller));
            given(productRepository.save(any())).willReturn(product);

            //when
            Long sellerId = sellerServiceImpl.createProduct(request, user);

            //then
            verify(productRepository, times(1)).save(any(Product.class));
            assertThat(sellerId).isEqualTo(product.getSeller().getId());
        }

        @Test
        @DisplayName("상품 등록 실패 - 일반유저")
        void productRegisterUnsuccess() {
            // given
            ProductRegistRequest request = TEST_PRODUCT_REGIST_REQUEST;
            User user = TEST_USER1;

            given(sellerRepository.findByUserId(anyLong())).willThrow(
                InvalidSellerEventException.class);

            // when & then
            assertThrows(InvalidSellerEventException.class, () -> {
                sellerServiceImpl.createProduct(request, user);
            });
        }
    }

}
