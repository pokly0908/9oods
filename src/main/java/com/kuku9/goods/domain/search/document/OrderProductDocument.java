package com.kuku9.goods.domain.search.document;

import jakarta.persistence.Id;
import java.util.UUID;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "order_products")
public class OrderProductDocument {

    @Id
    private final String id = UUID.randomUUID().toString();
    private Long productId;
    private int quantity;


}
