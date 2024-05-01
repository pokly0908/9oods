package com.kuku9.goods.domain.search.repository;

import com.kuku9.goods.domain.search.document.ProductDocument;
import java.util.List;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSearchRepository extends ElasticsearchRepository<ProductDocument, String> {

    void deleteByProductId(Long productId);

}
