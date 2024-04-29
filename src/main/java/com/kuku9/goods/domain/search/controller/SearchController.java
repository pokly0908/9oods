package com.kuku9.goods.domain.search.controller;

import com.kuku9.goods.domain.search.document.ProductDocument;
import com.kuku9.goods.domain.search.document.SellerDocument;
import com.kuku9.goods.domain.search.service.SearchService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/search")
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/product-name")
    public List<ProductDocument> searchProductName(String keyword) {
        return searchService.searchProductName(keyword);
    }

    @GetMapping("/product-introduce")
    public List<ProductDocument> searchProductIntroduce(String keyword) {
        return searchService.searchProductIntroduce(keyword);
    }

    // 상품 검색
    @GetMapping("/products")
    public List<ProductDocument> searchProduct(@RequestParam String keyword) {
        return searchService.searchProduct(keyword);
    }

    @GetMapping("/brand-name")
    public List<SellerDocument> searchBrandName(String keyword) {
        return searchService.searchBrandName(keyword);
    }

    @GetMapping("/brand-introduce")
    public List<SellerDocument> searchBrandIntroduce(String keyword) {
        return searchService.searchBrandIntroduce(keyword);
    }

    // 브랜드 검색
    @GetMapping("/brands")
    public List<SellerDocument> searchBrand(@RequestParam String keyword) {
        return searchService.searchBrand(keyword);
    }
}
