package com.kuku9.goods.seller;

import com.kuku9.goods.common.TestValue;
import com.kuku9.goods.domain.product.entity.Product;
import com.kuku9.goods.domain.product.repository.ProductRepository;
import com.kuku9.goods.domain.seller.dto.request.ProductRegistRequest;
import com.kuku9.goods.domain.seller.entity.Seller;
import com.kuku9.goods.domain.seller.repository.SellerRepository;
import com.kuku9.goods.domain.seller.service.SellerServiceImpl;
import com.kuku9.goods.domain.user.entity.User;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SellerServiceImplTest extends TestValue {

    @InjectMocks
    private SellerServiceImpl sellerServiceImpl;

    @Mock
    SellerRepository sellerRepository;

    @Mock
    ProductRepository productRepository;

    @Nested // 중첩 클래스 정의
    class CreateProductTest {

        @Test
        void 상품등록성공_셀러() {
            // given
            // 입력으로 상품에 대한 정보 입력받기 위해 Request 생성
            ProductRegistRequest request = TEST_PRODUCT_REGIST_REQUEST;

            Seller seller = TEST_SELLER;

            Product product = TEST_PRODUCT;

            given

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
