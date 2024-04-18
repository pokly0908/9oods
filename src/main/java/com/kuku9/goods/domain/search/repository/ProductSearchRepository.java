package com.kuku9.goods.domain.search.repository;

import com.kuku9.goods.domain.search.document.ProductDocument;
import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSearchRepository extends ElasticsearchRepository<ProductDocument, Long> {

    @Query
    List<ProductDocument> findAllByProductName(String keyword);

    List<ProductDocument> findAllByIntroduce(String keyword);

    List<ProductDocument> findAllByProductNameOrIntroduce(String keyword);

}
