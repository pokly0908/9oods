package com.kuku9.goods.domain.cart.service;

import static com.kuku9.goods.domain.user.entity.QUser.user;

import com.kuku9.goods.domain.cart.dto.CartRequest;
import com.kuku9.goods.domain.cart.dto.CartResponse;
import com.kuku9.goods.domain.cart.dto.CartsResponse;
import com.kuku9.goods.domain.cart.entity.Cart;
import com.kuku9.goods.domain.cart.repository.CartRepository;
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
public class CartService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    @Transactional
    public String createCart(User user, CartRequest request) {
        Long productId = request.getProductId();
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
        cartRepository.save(new Cart(product, request, user, "status"));
        return "장바구니에 상품이 추가되었습니다.";
    }
    @Transactional
    public CartsResponse getCart(User user) {
        List<Cart> cart = cartRepository.findAllByUserAndCartStatus(user, "장바구니");
        List<CartResponse> cartRespones = new ArrayList<>();
        for (Cart value : cart) {
            cartRespones.add(new CartResponse(value));
        }
        return new CartsResponse(cartRespones);
    }
}
