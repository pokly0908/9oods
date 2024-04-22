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

//    // 일반적인 검색 조건 product name
//    @GetMapping("/product-name")
//    public List<ProductDocument> searchProductName(@RequestParam String keyword) {
//        return searchService.searchProductName(keyword);
//    }
//
//    // 일반적인 검색 조건 product introduce
//    @GetMapping("/product-introduce")
//    public List<ProductDocument> searchProductIntroduce(@RequestParam String keyword) {
//        return searchService.searchProductIntroduce(keyword);
//    }

    // 일반적인 검색 조건 product name, introduct
    @GetMapping("/products")
    public List<ProductDocument> searchProduct(@RequestParam String keyword) {
        return searchService.searchProduct(keyword);
    }
//
//    // 일반적인 검색 조건 brand name
//    @GetMapping("/brand-name")
//    public List<SellerDocument> searchBrandName(@RequestParam String keyword) {
//        return searchService.searchBrandName(keyword);
//    }
//
//    // 일반적인 검색 조건 brand introduce
//    @GetMapping("/brand-introduce")
//    public List<SellerDocument> searchIntroduce(@RequestParam String keyword) {
//        return searchService.searchBrandIntroduce(keyword);
//    }

    // 일반적인 검색 조건 brand name, introduct
    @GetMapping("/brands")
    public List<SellerDocument> searchSeller(@RequestParam String keyword) {
        return searchService.searchbrand(keyword);
    }
}
