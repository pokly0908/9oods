package com.kuku9.goods.domain.search.service;

import com.kuku9.goods.domain.search.document.ProductDocument;
import com.kuku9.goods.domain.search.document.SellerDocument;
import com.kuku9.goods.domain.search.dto.SearchResponse;
import com.kuku9.goods.domain.search.repository.SearchQuery;
import com.kuku9.goods.domain.search.repository.SellerSearchRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final SearchQuery searchQuery;

    @Override
    @Transactional(readOnly = true)
    public List<ProductDocument> searchProduct(String keyword) {
        return searchQuery.searchByProductNameOrIntroduce(keyword);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SellerDocument> searchBrand(String keyword) {
        return searchQuery.searchByBrandNameOrIntroduce(keyword);
    }

}
