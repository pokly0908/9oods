package com.kuku9.goods.domain.order.dto;

import com.kuku9.goods.domain.order_product.dto.OrderProductRequest;
import java.util.List;
import lombok.Getter;
import lombok.Value;


@Value
public class OrdersRequest {

    List<OrderProductRequest> products;
    String address;
    Long issuedCouponId;

    public OrdersRequest(List<OrderProductRequest> products, String address, Long issuedCouponId) {
        this.products = products;
        this.address = address;
        this.issuedCouponId = issuedCouponId;

    }
}
