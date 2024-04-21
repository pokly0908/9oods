package com.kuku9.goods.common;

import com.kuku9.goods.domain.coupon.entity.Coupon;
import com.kuku9.goods.domain.event.entity.Event;
import com.kuku9.goods.domain.event_product.dto.EventProductRequest;
import com.kuku9.goods.domain.event_product.entity.EventProduct;
import com.kuku9.goods.domain.issued_coupon.entity.IssuedCoupon;
import com.kuku9.goods.domain.order.dto.OrdersRequest;
import com.kuku9.goods.domain.order.entity.Order;
import com.kuku9.goods.domain.order_product.dto.OrderProductRequest;
import com.kuku9.goods.domain.order_product.entity.OrderProduct;
import com.kuku9.goods.domain.product.entity.Product;
import com.kuku9.goods.domain.search.document.ProductDocument;
import com.kuku9.goods.domain.search.document.SellerDocument;
import com.kuku9.goods.domain.seller.dto.request.ProductQuantityRequest;
import com.kuku9.goods.domain.seller.dto.request.ProductRegistRequest;
import com.kuku9.goods.domain.seller.dto.request.ProductUpdateRequest;
import com.kuku9.goods.domain.seller.dto.response.SoldProductQuantityResponse;
import com.kuku9.goods.domain.seller.dto.response.SoldProductResponse;
import com.kuku9.goods.domain.seller.dto.response.SoldProductSumPriceResponse;
import com.kuku9.goods.domain.seller.entity.Seller;
import com.kuku9.goods.domain.user.entity.User;
import com.kuku9.goods.domain.user.entity.UserRegisterTypeEnum;
import com.kuku9.goods.domain.user.entity.UserRoleEnum;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.springframework.data.domain.*;
import org.springframework.scheduling.quartz.LocalDataSourceJobStore;

public class TestValue {

    public final static Long TEST_USER_ID1 = 1L;
    public final static String TEST_USERNAME1 = "이메일1@이메일1.com";
    public final static String TEST_REALNAME1 = "이름";
    public final static String TEST_PASSWORD1 = "!password1";
    public final static UserRoleEnum TEST_ROLE1 = UserRoleEnum.SELLER;

    public final static UserRegisterTypeEnum TEST_TYPE1 = UserRegisterTypeEnum.LOCAL;
    public final static User TEST_USER1 = new User(
        TEST_USER_ID1,
        TEST_USERNAME1,
        TEST_REALNAME1,
        TEST_PASSWORD1,
        TEST_ROLE1,
        TEST_TYPE1
    );
    public final static Long TEST_USER_ID2 = 2L;
    public final static String TEST_USERNAME2 = "이메일2@이메일2.com";
    public final static String TEST_REALNAME2 = "이름이름";
    public final static String TEST_PASSWORD2 = "!password2";
    public final static UserRoleEnum TEST_ROLE2 = UserRoleEnum.USER;
    public final static UserRegisterTypeEnum TEST_TYPE2 = UserRegisterTypeEnum.LOCAL;
    public final static User TEST_USER2 = new User(
        TEST_USER_ID2,
        TEST_USERNAME2,
        TEST_REALNAME2,
        TEST_PASSWORD2,
        TEST_ROLE2,
        TEST_TYPE2
    );
    public final static Long TEST_SELLER_ID1 = 1L;
    public final static String TEST_SELLER_BRANDNAME1 = "brand1";
    public final static String TEST_SELLER_DOMAINNAME1 = "domain1";
    public final static String TEST_SELLER_INTRODUCE1 = "introduce1";
    public final static String TEST_SELLER_EMAIL1 = "이메일1@이메일1.com";
    public final static String TEST_SELLER_PHONENEMBER1 = "00000001";
    public final static Seller TEST_SELLER = new Seller(
        TEST_SELLER_ID1,
        TEST_SELLER_BRANDNAME1,
        TEST_SELLER_DOMAINNAME1,
        TEST_SELLER_INTRODUCE1,
        TEST_SELLER_EMAIL1,
        TEST_SELLER_PHONENEMBER1,
        TEST_USER1
    );
    public final static Long TEST_SELLER_ID2 = 2L;
    public final static String TEST_SELLER_BRANDNAME2 = "brand2";
    public final static String TEST_SELLER_DOMAINNAME2 = "domain2";
    public final static String TEST_SELLER_INTRODUCE2 = "introduce2";
    public final static String TEST_SELLER_EMAIL2 = "이메일2@이메일2.com";
    public final static String TEST_SELLER_PHONENEMBER2 = "00000002";
    public final static Seller TEST_SELLER2 = new Seller(
        TEST_SELLER_ID2,
        TEST_SELLER_BRANDNAME2,
        TEST_SELLER_DOMAINNAME2,
        TEST_SELLER_INTRODUCE2,
        TEST_SELLER_EMAIL2,
        TEST_SELLER_PHONENEMBER2,
        TEST_USER2
    );
    public final static Long TEST_PRODUCT_ID1 = 1L;
    public final static String TEST_PRODUCT_NAME = "상품1";
    public final static String TEST_PRODUCT_DESCRIPTION = "상품 설명1";
    public final static int TEST_PRODUCT_PRICE = 100000;
    public final static int TEST_PRODUCT_QUANTITY = 1000;
    public final static Product TEST_PRODUCT = new Product(
        TEST_PRODUCT_ID1,
        TEST_SELLER,
        TEST_PRODUCT_NAME,
        TEST_PRODUCT_DESCRIPTION,
        TEST_PRODUCT_PRICE,
        true,
        TEST_PRODUCT_QUANTITY
    );
    public final static Long TEST_SELLER_ID = TEST_SELLER_ID1;
    public final static String TEST_REQUEST_PRODUCT_NAME = TEST_PRODUCT_NAME;
    public final static String TEST_REQUEST_PRODUCT_DESCRIPTION = TEST_PRODUCT_DESCRIPTION;
    public final static int TEST_REQUEST_PRODUCT_PRICE = TEST_PRODUCT_PRICE;
    public final static int TEST_REQUEST_PRODUCT_QUANTITY = TEST_PRODUCT_QUANTITY;
    public final static ProductRegistRequest TEST_PRODUCT_REGIST_REQUEST =
        new ProductRegistRequest(
            TEST_SELLER_ID,
            TEST_REQUEST_PRODUCT_NAME,
            TEST_REQUEST_PRODUCT_DESCRIPTION,
            TEST_REQUEST_PRODUCT_PRICE,
            TEST_REQUEST_PRODUCT_QUANTITY
        );
    public final static String TEST_ADDRESS = "서울시 강남구";
    public final static OrderProductRequest TEST_ORDER_PRODUCT_REQUEST = new OrderProductRequest(
        TEST_PRODUCT_ID1,
        1
    );
    public final static List<OrderProductRequest> TEST_ORDER_PRODUCT_REQUEST_LIST = List.of(
        TEST_ORDER_PRODUCT_REQUEST
    );
    public final static OrdersRequest TEST_ORDERS_REQUEST = new OrdersRequest(
        TEST_ORDER_PRODUCT_REQUEST_LIST,
        TEST_ADDRESS,
        1L
    );
    public final static Order TEST_ORDER = new Order(
        TEST_USER1,
        TEST_ADDRESS
    );
    public final static OrderProduct TEST_ORDER_PRODUCT = new OrderProduct(
        TEST_ORDER,
        TEST_PRODUCT,
        1
    );
    public final static Long TEST_COUPON_ID = 1L;
    public final static LocalDate TEST_COUPON_EXPIRATIONDATE = LocalDate.of(2024, 4, 15);
    public final static int TEST_COUPON_QUANTITY = 50;
    public final static String TEST_COUPON_CATEGORY = "ds";
    public final static Coupon TEST_COUPON = new Coupon(
        TEST_COUPON_ID,
        TEST_COUPON_EXPIRATIONDATE,
        TEST_COUPON_QUANTITY,
        TEST_COUPON_CATEGORY
    );
    public final static IssuedCoupon TEST_ISSUED_COUPON = new IssuedCoupon(
        1L,
        TEST_USER2,
        TEST_COUPON,
        null
    );
    public final static Long TEST_SIGNUP_COUPON_ID = 2L;
    public final static LocalDate TEST_SIGNUP_COUPON_EXPIRATIONDATE = LocalDate.of(2024, 4, 15);
    public final static int TEST_SIGNUP_COUPON_QUANTITY = 50;
    public final static String TEST_SIGNUP_COUPON_CATEGORY = "su";
    public final static Coupon TEST_SIGNUP_COUPON = new Coupon(
        TEST_SIGNUP_COUPON_ID,
        TEST_SIGNUP_COUPON_EXPIRATIONDATE,
        TEST_SIGNUP_COUPON_QUANTITY,
        TEST_SIGNUP_COUPON_CATEGORY
    );
    public final static IssuedCoupon TEST_ISSUED_COUPON2 = new IssuedCoupon(
        2L,
        TEST_USER2,
        TEST_SIGNUP_COUPON,
        null
    );
    public final static Long TEST_EVENT_ID = 1L;
    public final static String TEST_EVENT_TITLE = "제목";
    public final static String TEST_EVENT_CONTENT = "내용";
    public final static LocalDateTime TEST_EVENT_OPEN = LocalDateTime.now();
    public final static List<EventProductRequest> TEST_EVENT_EVENTPRODUCTS = List.of(
        new EventProductRequest(1L)
    );
    public final static Event TEST_EVENT = new Event(
        TEST_EVENT_ID,
        TEST_EVENT_TITLE,
        TEST_EVENT_CONTENT,
        TEST_EVENT_OPEN,
        TEST_USER1,
        TEST_COUPON
    );
    public final static EventProduct TEST_EVENTPRODUCT = new EventProduct(
        1L,
        TEST_EVENT,
        TEST_PRODUCT
    );
    public final static Long TEST_DOCUMENT_SELLER_ID = TEST_SELLER_ID1;
    public final static String TEST_DOCUMENT_PRODUCT_NAME = TEST_REQUEST_PRODUCT_NAME;
    public final static String TEST_DOCUMENT_PRODUCT_DESCRIPTION = TEST_REQUEST_PRODUCT_DESCRIPTION;
    public final static int TEST_DOCUMENT_PRODUCT_PRICE = TEST_REQUEST_PRODUCT_PRICE;
    public final static int TEST_DOCUMENT_PRODUCT_QUANTITY = TEST_REQUEST_PRODUCT_QUANTITY;
    public final static ProductDocument TEST_PRODUCT_DOCUMENT = new ProductDocument(
        TEST_DOCUMENT_SELLER_ID,
        TEST_DOCUMENT_PRODUCT_NAME,
        TEST_DOCUMENT_PRODUCT_DESCRIPTION,
        TEST_DOCUMENT_PRODUCT_PRICE,
        TEST_DOCUMENT_PRODUCT_QUANTITY
    );

    public final static Long TEST_DOCUMENT_USER_ID = TEST_USER_ID1;
    public final static String TEST_DOCUMENT_BRANDNAME = TEST_SELLER_BRANDNAME1;
    public final static String TEST_DOCUMENT_INTRODUCE = TEST_SELLER_INTRODUCE1;
    public final static SellerDocument TEST_SELLER_DOCUMENT = new SellerDocument(
        TEST_DOCUMENT_USER_ID,
        TEST_DOCUMENT_BRANDNAME,
        TEST_DOCUMENT_INTRODUCE
    );

    public final static int TEST_PRODUCT_UPDATE_QUANTITY = 1234;
    public final static ProductQuantityRequest TEST_PRODUCT_QUANTITY_REQUEST =
        new ProductQuantityRequest(TEST_PRODUCT_UPDATE_QUANTITY);

    public final static String TEST_PRODUCT_UPDATE_NAME = "수정된 상품 이름";
    public final static String TEST_PRODUCT_UPDATE_DESCRIPTION = "수정된 상품 설명";
    public final static int TEST_PRODUCT_UPDATE_PRICE = 1234;
    public final static ProductUpdateRequest TEST_PRODUCT_UPDATE_REQUEST =
        new ProductUpdateRequest(
            TEST_PRODUCT_UPDATE_NAME,
            TEST_PRODUCT_UPDATE_DESCRIPTION,
            TEST_PRODUCT_UPDATE_PRICE
        );

    public final static int page = 0;
    public final static int size = 20;
    public final static Pageable TEST_PAGEABLE = PageRequest.of(
        page,
        size,
        Sort.by(Sort.Direction.DESC, "name")
    );
    public final static LocalDate TEST_START_DATE = LocalDate.parse("2024-01-01");
    public final static LocalDate TEST_END_DATE = LocalDate.parse("2024-12-30");

    public final static Long TEST_PRODUCT_ID2 = 2L;
    public final static String TEST_PRODUCT_NAME2 = "상품2";
    public final static String TEST_PRODUCT_DESCRIPTION2 = "상품 설명2";
    public final static int TEST_PRODUCT_PRICE2 = 200000;
    public final static int TEST_PRODUCT_QUANTITY2 = 2000;
    public final static Product TEST_PRODUCT2 = new Product(
        TEST_PRODUCT_ID2,
        TEST_SELLER,
        TEST_PRODUCT_NAME2,
        TEST_PRODUCT_DESCRIPTION2,
        TEST_PRODUCT_PRICE2,
        true,
        TEST_PRODUCT_QUANTITY2
    );

    public final static Long TEST_PRODUCT_ID3 = 3L;
    public final static String TEST_PRODUCT_NAME3 = "상품3";
    public final static String TEST_PRODUCT_DESCRIPTION3 = "상품 설명3";
    public final static int TEST_PRODUCT_PRICE3 = 300000;
    public final static int TEST_PRODUCT_QUANTITY3 = 3000;
    public final static Product TEST_PRODUCT3 = new Product(
        TEST_PRODUCT_ID3,
        TEST_SELLER,
        TEST_PRODUCT_NAME3,
        TEST_PRODUCT_DESCRIPTION3,
        TEST_PRODUCT_PRICE3,
        true,
        TEST_PRODUCT_QUANTITY3
    );

    public final static List<SoldProductResponse> soldProductList = Arrays.asList(
        new SoldProductResponse(
            TEST_PRODUCT_NAME,
            TEST_PRODUCT_PRICE,
            TEST_PRODUCT_QUANTITY,
            10,
            10000000,
            LocalDateTime.now()
        ),
        new SoldProductResponse(
            TEST_PRODUCT_NAME2,
            TEST_PRODUCT_PRICE2,
            TEST_PRODUCT_QUANTITY2,
            20,
            20000000,
            LocalDateTime.now()
        ),
        new SoldProductResponse(
            TEST_PRODUCT_NAME3,
            TEST_PRODUCT_PRICE3,
            TEST_PRODUCT_QUANTITY3,
            30,
            30000000,
            LocalDateTime.now()
        )
    );
    public final static Page<SoldProductResponse> TEST_SOLD_PRODUCT_LIST =
        new PageImpl<>(soldProductList);

    public final static int TEST_TOTAL_PRICE =
        TEST_PRODUCT_PRICE + TEST_PRODUCT_PRICE2 + TEST_PRODUCT_PRICE3;
    public final static SoldProductSumPriceResponse TEST_SOLD_PRODUCT_SUM_PRICE_RESPONSE =
        new SoldProductSumPriceResponse(
            TEST_SELLER_BRANDNAME1,
            TEST_TOTAL_PRICE
        );

    public final static List<SoldProductQuantityResponse> TEST_SOLD_PRODUCT_QUANTITY_RESPONSE =
        Arrays.asList(
            new SoldProductQuantityResponse(
                TEST_PRODUCT_NAME,
                TEST_PRODUCT_QUANTITY
            ),
            new SoldProductQuantityResponse(
                TEST_PRODUCT_NAME2,
                TEST_PRODUCT_QUANTITY2
            ),
            new SoldProductQuantityResponse(
                TEST_PRODUCT_NAME3,
                TEST_PRODUCT_QUANTITY3
            ));
}

