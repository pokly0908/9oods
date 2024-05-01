package com.kuku9.goods.domain.search.repository;

import com.kuku9.goods.domain.search.document.ProductDocument;
import com.kuku9.goods.domain.search.document.SellerDocument;
import com.kuku9.goods.domain.search.dto.SearchResponse;
import java.util.List;

public interface SearchQuery {

    List<ProductDocument> searchByProductNameOrIntroduce(String keyword);

    List<SellerDocument> searchByBrandNameOrIntroduce(String keyword);

}
