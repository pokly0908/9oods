package com.kuku9.goods.domain.product_order.dto;

import com.kuku9.goods.domain.product.dto.ProductResponse;
import com.kuku9.goods.domain.product_order.entity.ProductOrder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ProductOrderResponse {

    private Long orderId;
    private String orderStatus;
    private LocalDateTime orderDate;
    private String address;
    private List<ProductResponse> products;

    public ProductOrderResponse(ProductOrder productOrder, List<ProductResponse> products) {
        this.orderId = productOrder.getId();
        this.orderStatus = productOrder.getStatus();
        this.orderDate = productOrder.getCreatedAt();
        this.address = productOrder.getAddress();
        this.products = products;
    }
}
