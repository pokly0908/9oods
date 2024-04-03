package com.kuku9.goods.domain.order.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kuku9.goods.domain.order.dto.OrderResponse;
import com.kuku9.goods.domain.order.dto.OrdersRequest;
import com.kuku9.goods.domain.order.entity.Order;
import com.kuku9.goods.domain.order.service.OrderService;
import com.kuku9.goods.global.security.CustomUserDetails;
import jakarta.transaction.Transactional;
import java.net.URI;
import java.nio.file.AccessDeniedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService productOrderService;

    @PostMapping
    @Transactional
    public ResponseEntity<String> createOrder(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @RequestBody OrdersRequest productOrderRequest) {
        Order order = productOrderService.createOrder(userDetails.getUser(),
            productOrderRequest);
        return ResponseEntity.created(URI.create("/api/v1/order/" + order.getId())).build();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(
        @AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long orderId)
        throws AccessDeniedException {
        OrderResponse productOrder = productOrderService.getOrder(userDetails.getUser(),
            orderId);
        return ResponseEntity.ok(productOrder);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderResponse> updateOrder(
        @AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long orderId)
        throws AccessDeniedException {
        OrderResponse productOrder = productOrderService.updateOrder(userDetails.getUser(),
            orderId);
        return ResponseEntity.ok(productOrder);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @PathVariable Long orderId) throws AccessDeniedException {
        productOrderService.deleteOrder(userDetails.getUser(), orderId);
        return ResponseEntity.noContent().build();
    }
}
