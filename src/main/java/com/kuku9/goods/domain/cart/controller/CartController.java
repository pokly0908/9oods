package com.kuku9.goods.domain.cart.controller;

import static com.kuku9.goods.domain.user.entity.QUser.user;
import static com.kuku9.goods.domain.user.entity.UserRoleEnum.USER;

import com.kuku9.goods.domain.cart.dto.CartRequest;
import com.kuku9.goods.domain.cart.dto.CartsResponse;
import com.kuku9.goods.domain.cart.service.CartService;
import com.kuku9.goods.domain.user.entity.User;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/carts")
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<String> createCart(//@AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody CartRequest requestDto) {
        User user = new User("pokly0908", "유경진", "pokly0908@naver.com", "accel@0908", "", USER);
        return ResponseEntity.created(URI.create("/api/v1/carts"))
            .body(cartService.createCart(user, requestDto));
    }
//
    @GetMapping
    public ResponseEntity<CartsResponse> getCart() {
        User user = new User("pokly0908", "유경진", "pokly0908@naver.com", "accel@0908", "", USER);
        return ResponseEntity.ok()
            .body(cartService.getCart(user));
    }

//    @PutMapping("/{cartId}")
//    public ResponseEntity<String> updateCart(@PathVariable Long cartId,
//        @AuthenticationPrincipal UserDetailsImpl userDetails,
//        @RequestBody CartRequest requestDto) {
//        cartService.updateCart(cartId, requestDto, userDetails.getUser());;
//    }
//
//    @DeleteMapping("/{cartId}")
//    public ResponseEntity<String> deleteCart(@PathVariable Long cartId,
//        @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        cartService.deleteCart(cartId, userDetails.getUser());
//    }
}

