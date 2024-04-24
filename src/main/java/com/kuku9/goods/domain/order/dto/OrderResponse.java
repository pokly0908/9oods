package com.kuku9.goods.domain.order.dto;

import com.kuku9.goods.domain.order.entity.Order;
import com.kuku9.goods.domain.order.entity.OrderStatus;
import com.kuku9.goods.domain.product.dto.ProductResponse;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;


@Value
@NoArgsConstructor(force = true)
public class OrderResponse {


    Long orderId;
    OrderStatus orderStatus;
    String orderDate;
    String address;
    List<ProductResponse> products;

    public OrderResponse(Long orderId, OrderStatus orderStatus, String orderDate, String address,
        List<ProductResponse> products) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
        this.address = address;
        this.products = products;
    }



    public static OrderResponse from(Order order,List<ProductResponse> products) {
        return new OrderResponse(
            order.getId(),
            order.getStatus(),
            order.getAddress(),
            order.getCreatedAt().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)),
            products);
    }
}