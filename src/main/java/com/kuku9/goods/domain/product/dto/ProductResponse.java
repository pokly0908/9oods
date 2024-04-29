package com.kuku9.goods.domain.product.dto;

import com.kuku9.goods.domain.product.entity.Product;
import lombok.*;

@Getter
@Value
@NoArgsConstructor(force = true)
public class ProductResponse {

    Long productId;
    String productName;
    String productDescription;
    int productPrice;
    String domainName;
    String brandName;

    public ProductResponse(Product product) {
        this.productId = product.getId();
        this.productName = product.getName();
        this.productDescription = product.getDescription();
        this.productPrice = product.getPrice();
        this.domainName = product.getSeller().getDomainName();
        this.brandName = product.getSeller().getBrandName();
    }
}

