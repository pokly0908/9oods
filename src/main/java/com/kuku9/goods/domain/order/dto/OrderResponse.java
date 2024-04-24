package com.kuku9.goods.domain.order.dto;

import com.kuku9.goods.domain.order.entity.Order;
import com.kuku9.goods.domain.order.entity.OrderStatus;
import com.kuku9.goods.domain.product.dto.ProductResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Value
@NoArgsConstructor(force = true)
public class OrderResponse {

    Long orderId;
    OrderStatus orderStatus;
    String orderDate;
    String address;
    List<ProductResponse> products;

    public OrderResponse(Order order, List<ProductResponse> products) {
        this.orderId = order.getId();
        this.orderStatus = order.getStatus();
        this.orderDate = order.getCreatedAt().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL));
        this.address = order.getAddress();
        this.products = products;
    }

}
