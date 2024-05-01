package com.kuku9.goods.domain.search.repository;

import com.kuku9.goods.domain.search.document.ProductDocument;
import com.kuku9.goods.domain.search.document.SellerDocument;
import com.kuku9.goods.domain.search.dto.SearchResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SearchQueryImpl implements SearchQuery {

    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public List<ProductDocument> searchByProductNameOrIntroduce(String keyword) {
        Criteria criteria = new Criteria("productName").is(keyword)
            .or("introduce").is(keyword);
        Query query = new CriteriaQuery(criteria);

        SearchHits<ProductDocument> searchHits =
            elasticsearchOperations.search(query, ProductDocument.class);

        return searchHits.getSearchHits()
            .stream()
            .map(SearchHit::getContent)
            .collect(Collectors.toList());
    }

    @Override
    public List<SellerDocument> searchByBrandNameOrIntroduce(String keyword) {
        Criteria criteria = new Criteria("brandName").is(keyword)
            .or("introduce").is(keyword);
        Query query = new CriteriaQuery(criteria);

        SearchHits<SellerDocument> searchHits =
            elasticsearchOperations.search(query, SellerDocument.class);

        return searchHits.getSearchHits()
            .stream()
            .map(SearchHit::getContent)
            .collect(Collectors.toList());
    }

}
