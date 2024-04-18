//package com.kuku9.goods.dummy;
//
//import com.kuku9.goods.domain.order.dto.OrdersRequest;
//import com.kuku9.goods.domain.order.entity.Order;
//import com.kuku9.goods.domain.order.repository.OrderRepository;
//import com.kuku9.goods.domain.order_product.dto.OrderProductRequest;
//import com.kuku9.goods.domain.order_product.entity.OrderProduct;
//import com.kuku9.goods.domain.order_product.repository.OrderProductRepository;
//import com.kuku9.goods.domain.product.entity.Product;
//import com.kuku9.goods.domain.product.repository.ProductJpaRepository;
//import com.kuku9.goods.domain.product.repository.ProductRepository;
//import com.kuku9.goods.domain.search.document.ProductDocument;
//import com.kuku9.goods.domain.search.document.SellerDocument;
//import com.kuku9.goods.domain.search.repository.ProductSearchRepository;
//import com.kuku9.goods.domain.search.repository.SellerSearchRepository;
//import com.kuku9.goods.domain.seller.dto.request.ProductRegistRequest;
//import com.kuku9.goods.domain.seller.entity.Seller;
//import com.kuku9.goods.domain.seller.repository.SellerRepository;
//import com.kuku9.goods.domain.user.dto.request.RegisterSellerRequest;
//import com.kuku9.goods.domain.user.dto.request.UserSignupRequest;
//import com.kuku9.goods.domain.user.entity.User;
//import com.kuku9.goods.domain.user.entity.UserRoleEnum;
//import com.kuku9.goods.domain.user.repository.UserRepository;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//import org.junit.jupiter.api.MethodOrderer;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestMethodOrder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.annotation.Commit;
//import org.springframework.transaction.annotation.Transactional;
//
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@SpringBootTest
//public class TestDummy {
//
//    @Autowired
//    private ProductRepository productRepository;
//    @Autowired
//    private ProductJpaRepository productJpaRepository;
//    @Autowired
//    private ProductSearchRepository productSearchRepository;
//    @Autowired
//    private SellerRepository sellerRepository;
//    @Autowired
//    private SellerSearchRepository sellerSearchRepository;
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private OrderProductRepository orderProductRepository;
//    @Autowired
//    private OrderRepository orderRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    // 유저 더미데이터
//    @Test
//    @Transactional
//    @Commit
//    public void testUserDummy() {
//        for (int i = 1; i <= 300; i++) {
//            UserSignupRequest request = UserSignupRequest.builder()
//                .realName("Tester " + i)
//                .username("email" + i + "@email" + i + ".com")
//                .password("password!@" + i)
//                .build();
//
//            User user = User.from(request, passwordEncoder.encode(request.getPassword()));
//
//            userRepository.save(user);
//        }
//    }
//
//    // 셀러 더미데이터
//    @Test
//    @Transactional
//    @Commit
//    public void testSellerDummy() {
//        List<User> users = userRepository.findAll();
//        Random random = new Random();
//
//        if (!users.isEmpty()) {
//            for (int i = 1; i <= 100; i++) {
//                User user = users.get(i * 2);
//                RegisterSellerRequest request = RegisterSellerRequest.builder()
//                    .brandName("brand name" + i)
//                    .domainName("domain name" + i)
//                    .introduce("brand에 대한 모든 것, 설명서 / 소개글" + i)
//                    .email("email" + i + "@email" + i + ".com")
//                    .phoneNumber((random.nextInt(90000000) + 10000000) + "").build();
//
//                user.updateRole(UserRoleEnum.SELLER);
//                Seller seller = Seller.from(request, user);
//
//                sellerRepository.save(seller);
//            }
//        } else {
//            System.out.println("유저 정보 확인");
//        }
//
//    }
//
//    // ES 셀러 더미데이터
//    @Test
//    @Transactional
//    @Commit
//    public void elasticSellerDummy() {
//        List<Seller> sellers = sellerRepository.findAll();
//
//        for (Seller seller : sellers) {
//            SellerDocument sellerDocument = new SellerDocument(
//                seller.getUser().getId(),
//                seller.getBrandName(),
//                seller.getIntroduce()
//            );
//
//            sellerSearchRepository.save(sellerDocument);
//        }
//    }
//
//    // 상품 더미데이터
//    @Test
//    @Transactional
//    @Commit
//    public void testProductDummy() {
//        List<Seller> sellers = sellerRepository.findAll();
//        Random random = new Random();
//
//        for (int i = 1; i <= 100000; i++) {
//            Seller seller = sellers.get(random.nextInt(sellers.size()));
//            int price = random.nextInt(100001) + 10000;
//            int quantity = random.nextInt(1001) + 500;
//
//            ProductRegistRequest request = ProductRegistRequest.builder()
//                .productName("Test Product " + i)
//                .productDescription("Test Description " + i)
//                .productPrice(price)
//                .productQuantity(quantity)
//                .build();
//
//            Product product = new Product(request, seller);
//            productRepository.save(product);
//        }
//    }
//
//    // ES 상품 더미데이터
//    @Test
//    @Transactional
//    @Commit
//    public void elasticProductDummy() {
//        List<Product> products = productJpaRepository.findAll();
//        for (Product product : products) {
//            ProductDocument productDocument = new ProductDocument(
//                product.getSeller().getId(),
//                product.getName(),
//                product.getDescription(),
//                product.getPrice(),
//                product.getQuantity());
//
//            productSearchRepository.save(productDocument);
//        }
//    }
//
//    // 주문 조회
//    @Test
//    @Transactional
//    @Commit
//    public void orderDummy() {
//        for (int i = 0; i < 150; i++) {
//            Random random = new Random();
//            List<User> users = userRepository.findAll();
//            List<OrderProductRequest> orderProductRequest = new ArrayList<>();
//            List<Product> products = new ArrayList<>();
//            for (int j = 0; j < random.nextInt(100) + 1; j++) {
//                orderProductRequest.add(new OrderProductRequest(
//                    random.nextLong(150000) + 1,
//                    random.nextInt(100)));
//            }
//            OrdersRequest ordersRequest = new OrdersRequest(
//                orderProductRequest,
//                "경기도 남양주시 별내동 " +
//                    random.nextInt(999) + 1 + "동 " +
//                    random.nextInt(999) + 1 + "호",
//                random.nextLong(100) + 1);
//
//            for (int j = 0; j < ordersRequest.getProducts().size(); j++) {
//                products.add(productRepository.findById(
//                    ordersRequest.getProducts().get(j).getProductId()).orElseThrow());
//            }
//
//            Order order = orderRepository.save(
//                new Order(
//                    users.get(random.nextInt(100)),
//                    ordersRequest.getAddress()));
//
//            for (int j = 0; j < products.size(); j++) {
//                OrderProduct orderProduct = new OrderProduct(
//                    order,
//                    products.get(j),
//                    ordersRequest.getProducts().get(j).getQuantity());
//
//            orderProductRepository.save(orderProduct);
//            }
//        }
//    }
//
//}
