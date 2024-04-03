package com.kuku9.goods.domain.product_order.controller;

import com.kuku9.goods.domain.product_order.dto.ProductOrderResponse;
import com.kuku9.goods.domain.product_order.dto.ProductOrdersRequest;
import com.kuku9.goods.domain.product_order.entity.ProductOrder;
import com.kuku9.goods.domain.product_order.service.ProductOrderService;
import com.kuku9.goods.global.security.CustomUserDetails;
import java.net.URI;
import java.nio.file.AccessDeniedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
        @AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long orderId)
        throws AccessDeniedException {
        ProductOrderResponse productOrder = productOrderService.getOrder(userDetails.getUser(),
            orderId);
        return ResponseEntity.ok(productOrder);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<ProductOrderResponse> updateOrder(
        @AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long orderId)
        throws AccessDeniedException {
        ProductOrderResponse productOrder = productOrderService.updateOrder(userDetails.getUser(),
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
