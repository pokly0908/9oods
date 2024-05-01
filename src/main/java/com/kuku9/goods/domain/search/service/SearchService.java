package com.kuku9.goods.domain.search.service;

import com.kuku9.goods.domain.search.document.ProductDocument;
import com.kuku9.goods.domain.search.document.SellerDocument;
import com.kuku9.goods.domain.search.dto.SearchResponse;
import java.util.List;
import org.springframework.data.elasticsearch.core.SearchHits;

public interface SearchService {

    List<ProductDocument> searchProduct(String keyword);

    List<SellerDocument> searchBrand(String keyword);

}
