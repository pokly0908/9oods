package com.kuku9.goods.domain.search.controller;

import com.kuku9.goods.domain.search.document.ProductDocument;
import com.kuku9.goods.domain.search.document.SellerDocument;
import com.kuku9.goods.domain.search.service.SearchService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public List<SellerDocument> searchSeller(@RequestParam String keyword) {
        return searchService.searchbrand(keyword);
    }
}
