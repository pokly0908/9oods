package com.kuku9.goods.domain.event.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductInfo {

    private String name;
    private String description;
    private String price;

}
