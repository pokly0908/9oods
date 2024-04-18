package com.kuku9.goods.domain.order.dto;

import com.kuku9.goods.domain.order.entity.Order;
import com.kuku9.goods.domain.order.entity.OrderStatus;
import com.kuku9.goods.domain.product.dto.ProductResponse;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Value;

@Getter
@Value
public class OrderResponse {

    Long orderId;
	OrderStatus orderStatus;
    LocalDateTime orderDate;
    String address;
    List<ProductResponse> products;

	public OrderResponse(Order order, List<ProductResponse> products) {
		this.orderId = order.getId();
		this.orderStatus = order.getStatus();
		this.orderDate = order.getCreatedAt();
		this.address = order.getAddress();
		this.products = products;
	}
}
