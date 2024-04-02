package com.kuku9.goods.domain.product_order.dto;

import com.kuku9.goods.domain.product.dto.*;
import com.kuku9.goods.domain.product_order.entity.*;
import java.time.*;
import java.util.*;
import lombok.*;

@Getter
public class ProductOrderResponse {

    private final Long orderId;
    private final String orderStatus;
    private final LocalDateTime orderDate;
    private final String address;
    private final List<ProductResponse> products;

    public ProductOrderResponse(ProductOrder productOrder, List<ProductResponse> products) {
        this.orderId = productOrder.getId();
        this.orderStatus = productOrder.getStatus();
        this.orderDate = productOrder.getCreatedAt();
        this.address = productOrder.getAddress();
        this.products = products;
    }
}
