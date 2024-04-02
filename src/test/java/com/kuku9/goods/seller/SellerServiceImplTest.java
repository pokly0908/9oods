package com.kuku9.goods.seller;

import com.kuku9.goods.common.*;
import com.kuku9.goods.domain.product.entity.*;
import com.kuku9.goods.domain.product.repository.*;
import com.kuku9.goods.domain.seller.dto.request.*;
import com.kuku9.goods.domain.seller.entity.*;
import com.kuku9.goods.domain.seller.repository.*;
import com.kuku9.goods.domain.seller.service.*;
import com.kuku9.goods.domain.user.entity.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

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
        void 상품등록성공_셀러() {
            // given
            // 입력으로 상품에 대한 정보 입력받기 위해 Request 생성
            ProductRegistRequest request = TEST_PRODUCT_REGIST_REQUEST;

            Seller seller = TEST_SELLER;

            Product product = TEST_PRODUCT;

            //when

            //then
        }

        @Test
        void 상품등록실패_유저() {
            // given
            ProductRegistRequest request = TEST_PRODUCT_REGIST_REQUEST;

            User user = TEST_USER1;

            Product product = TEST_PRODUCT;
        }
    }

}
