package com.kuku9.goods.domain.seller.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SoldProductResponse {

    private final String productName;
    private final int productPrice;
    private final int productQuantity;
    private final int orderProductQuantity;
    private final int productTotalPrice;
    private final LocalDateTime orderTime;

}
