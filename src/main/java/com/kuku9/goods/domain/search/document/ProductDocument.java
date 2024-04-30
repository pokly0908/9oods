package com.kuku9.goods.domain.search.document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "products")
@Setting(settingPath = "elasticsearch/product-setting.json")
@Mapping(mappingPath = "elasticsearch/product-mapping.json")
public class ProductDocument {

    @Id
    private String id;
    private Long sellerId;
    private Long productId;
    private String productName;
    private String introduce;
    private int price;
    private int quantity;

    public ProductDocument(
        Long id, Long productId,
        String productName, String productDescription,
        int productPrice, int productQuantity) {
        this.sellerId = id;
        this.productId = productId;
        this.productName = productName;
        this.introduce = productDescription;
        this.price = productPrice;
        this.quantity = productQuantity;
    }
}
