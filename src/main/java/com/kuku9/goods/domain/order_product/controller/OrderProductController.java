package com.kuku9.goods.domain.order_product.controller;

import static com.kuku9.goods.domain.user.entity.UserRoleEnum.USER;

import com.kuku9.goods.domain.order_product.dto.OrderProductRequest;
import com.kuku9.goods.domain.order_product.dto.OrderProductsResponse;
import com.kuku9.goods.domain.order_product.service.OrderProductService;
import com.kuku9.goods.domain.user.entity.User;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
public class OrderProductController {

    private final OrderProductService orderProductService;

//    @PostMapping
//    public ResponseEntity<String> createCart(//@AuthenticationPrincipal UserDetailsImpl userDetails,
//        @RequestBody OrderProductRequest requestDto) {
//        return ResponseEntity.created(URI.create("/api/v1/carts"))
//            .body(orderProductService.createCart(user, requestDto));
//    }
//
//    @GetMapping
//    public ResponseEntity<OrderProductsResponse> getCart() {
//        return ResponseEntity.ok()
//            .body(orderProductService.getCart(user));
//    }
//
//    @PutMapping("/{cartId}")
//    public ResponseEntity<String> updateCart(@PathVariable Long cartId,
//        //@AuthenticationPrincipal UserDetailsImpl userDetails,
//        @RequestBody OrderProductRequest request) {
//        orderProductService.updateCart(cartId, request, user);
//        return ResponseEntity.ok().body("장바구니가 수정되었습니다.");
//    }
//
//    @DeleteMapping("/{cartId}")
//    public ResponseEntity<String> deleteCart(@PathVariable Long cartId)//, @AuthenticationPrincipal UserDetailsImpl userDetails)
//        {
//
//        orderProductService.deleteCart(cartId, user);
//        return ResponseEntity.notFound().build();
//    }
}

