package com.kuku9.goods.domain.search.controller;

import com.kuku9.goods.domain.search.document.ProductDocument;
import com.kuku9.goods.domain.search.document.SellerDocument;
import com.kuku9.goods.domain.search.dto.SearchResponse;
import com.kuku9.goods.domain.search.service.SearchService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/search")
public class SearchController {

    private final SearchService searchService;

    // 상품 검색
    @GetMapping("/products")
    public List<ProductDocument> searchProduct(@RequestParam String keyword) {
        return searchService.searchProduct(keyword);
    }

    // 브랜드 검색
    @GetMapping("/brands")
    public List<SellerDocument> searchBrand(@RequestParam String keyword) {
        return searchService.searchBrand(keyword);
    }

}
