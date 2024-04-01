package com.kuku9.goods.domain.product_order.dto;

import com.kuku9.goods.domain.order_product.dto.OrderProductResponse;
import com.kuku9.goods.domain.order_product.entity.OrderProduct;
import com.kuku9.goods.domain.product.dto.ProductResponse;
import com.kuku9.goods.domain.product_order.entity.ProductOrder;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

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
