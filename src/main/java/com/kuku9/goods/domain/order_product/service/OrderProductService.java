package com.kuku9.goods.domain.order_product.service;

import com.kuku9.goods.domain.order_product.dto.OrderProductRequest;
import com.kuku9.goods.domain.order_product.dto.OrderProductResponse;
import com.kuku9.goods.domain.order_product.dto.OrderProductsResponse;
import com.kuku9.goods.domain.order_product.entity.OrderProduct;
import com.kuku9.goods.domain.order_product.repository.OrderProductRepository;
import com.kuku9.goods.domain.product.entity.Product;
import com.kuku9.goods.domain.product.repository.ProductRepository;
import com.kuku9.goods.domain.user.entity.User;
import com.kuku9.goods.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderProductService {
    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;
    private final UserRepository userRepository;

//    @Transactional
//    public String createCart(User user, OrderProductRequest request) {
//        Long productId = request.getProductId();
//        Product product = productRepository.findById(productId)
//            .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
//        orderProductRepository.save(new OrderProduct(product, request, user, "status"));
//        return "장바구니에 상품이 추가되었습니다.";
//    }
//    @Transactional
//    public OrderProductsResponse getCart(User user) {
//        List<OrderProduct> cart = orderProductRepository.findAllByUserAndStatus(user, "장바구니");
//        List<OrderProductResponse> cartRespones = new ArrayList<>();
//        for (OrderProduct value : cart) {
//            cartRespones.add(new OrderProductResponse(value));
//        }
//        return new OrderProductsResponse(cartRespones);
//    }
//
//    public void updateCart(Long cartId, OrderProductRequest request, User user) {
//        OrderProduct cart = orderProductRepository.findById(cartId)
//            .orElseThrow(() -> new IllegalArgumentException("장바구니를 찾을 수 없습니다."));
//        if(!cart.getUser().getId().equals(user.getId())){
//            throw new IllegalArgumentException("수정할 수 없습니다.");
//        }
//        Long productId = request.getProductId();
//        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
//        cart.update(product, request);
//    }
//
//    public void deleteCart(Long cartId, User user) {
//        OrderProduct cart = orderProductRepository.findById(cartId)
//            .orElseThrow(() -> new IllegalArgumentException("장바구니를 찾을 수 없습니다."));
//        if(!cart.getUser().getId().equals(user.getId())){
//            throw new IllegalArgumentException("수정할 수 없습니다.");
//        }
//        orderProductRepository.delete(cart);
//    }
}
