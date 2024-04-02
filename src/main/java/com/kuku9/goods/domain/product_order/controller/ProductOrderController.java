package com.kuku9.goods.domain.product_order.controller;

import com.kuku9.goods.domain.product_order.dto.*;
import com.kuku9.goods.domain.product_order.entity.*;
import com.kuku9.goods.domain.product_order.service.*;
import com.kuku9.goods.global.security.*;
import java.net.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.security.core.annotation.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class ProductOrderController {

    private final ProductOrderService productOrderService;

    @PostMapping
    public ResponseEntity<String> createOrder(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @RequestBody ProductOrdersRequest productOrderRequest) {
        ProductOrder productOrder = productOrderService.createOrder(userDetails.getUser(),
            productOrderRequest);
        return ResponseEntity.created(URI.create("/api/v1/order/" + productOrder.getId())).build();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ProductOrderResponse> getOrder(
        @AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long orderId) {
        ProductOrderResponse productOrder = productOrderService.getOrder(userDetails.getUser(),
            orderId);
        return ResponseEntity.ok(productOrder);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<ProductOrderResponse> updateOrder(
        @AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long orderId) {
        ProductOrderResponse productOrder = productOrderService.updateOrder(userDetails.getUser(),
            orderId);
        return ResponseEntity.ok(productOrder);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @PathVariable Long orderId) {
        productOrderService.deleteOrder(userDetails.getUser(), orderId);
        return ResponseEntity.noContent().build();
    }
}
