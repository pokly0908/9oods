package com.kuku9.goods.domain.search.repository;

import com.kuku9.goods.domain.search.document.SellerDocument;
import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerSearchRepository extends ElasticsearchRepository<SellerDocument, String> {

    List<SellerDocument> findAllByBrandName(String keyword);

    List<SellerDocument> findAllByIntroduce(String keyword);

    List<SellerDocument> findAllByBrandNameOrIntroduce(String keyword);
}
