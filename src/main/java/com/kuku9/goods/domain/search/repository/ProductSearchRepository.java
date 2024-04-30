package com.kuku9.goods.domain.search.repository;

import com.kuku9.goods.domain.search.document.ProductDocument;
import java.util.List;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSearchRepository extends ElasticsearchRepository<ProductDocument, String> {

    List<ProductDocument> findAllByProductName(String keyword);

    List<ProductDocument> findAllByIntroduce(String keyword);

    @Query("{\"bool\": {\"should\": [{\"match\": {\"productName\": \"?0\"}}, {\"match\": {\"introduce\": \"?0\"}}]}}")
    List<ProductDocument> searchByProductNameOrIntroduce(String keyword);

    void deleteByProductId(Long productId);

}
