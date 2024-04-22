package com.kuku9.goods.domain.search.document;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "sellers")
@Setting(settingPath = "elasticsearch/seller-setting.json")
@Mapping(mappingPath = "elasticsearch/seller-mapping.json")
public class SellerDocument {

    @Id
    private String id;
    private Long userId;
    private String brandName;
    private String introduce;

    public SellerDocument(Long id, String brandName, String introduce) {
        this.userId = id;
        this.brandName = brandName;
        this.introduce = introduce;
    }
}
