package com.kuku9.goods.domain.product_order.dto;

import com.kuku9.goods.domain.order_product.dto.*;
import java.util.*;
import lombok.*;

@Getter
@RequiredArgsConstructor
public class ProductOrdersRequest {

    private List<OrderProductRequest> products;
    private String address;

    public ProductOrdersRequest(List<OrderProductRequest> products, String address) {
        this.products = products;
        this.address = address;

    }
}
