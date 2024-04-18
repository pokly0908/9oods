package com.kuku9.goods.domain.seller.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Value;

@Getter
@Value
public class SoldProductResponse {

    String productName;
    int productPrice;
    int productQuantity;
    int orderProductQuantity;
    int productTotalPrice;
    LocalDateTime orderTime;

}
