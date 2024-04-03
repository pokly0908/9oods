package com.kuku9.goods.domain.order.dto;

import com.kuku9.goods.domain.product.dto.ProductResponse;
import com.kuku9.goods.domain.order.entity.Order;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
public class OrderResponse {

    private final Long orderId;
    private final String orderStatus;
    private final LocalDateTime orderDate;
    private final String address;
    private final List<ProductResponse> products;

	public OrderResponse(Order order, List<ProductResponse> products) {
		this.orderId = order.getId();
		this.orderStatus = order.getStatus();
		this.orderDate = order.getCreatedAt();
		this.address = order.getAddress();
		this.products = products;
	}
}
