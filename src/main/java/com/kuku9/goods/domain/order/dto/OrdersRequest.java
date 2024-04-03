package com.kuku9.goods.domain.order.dto;

import com.kuku9.goods.domain.order_product.dto.OrderProductRequest;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrdersRequest {

    private List<OrderProductRequest> products;
    private String address;

    public OrdersRequest(List<OrderProductRequest> products, String address) {
        this.products = products;
        this.address = address;

    }
}
