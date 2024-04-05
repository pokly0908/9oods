package com.kuku9.goods.domain.search.controller;

import com.kuku9.goods.domain.search.dto.SearchResponse;
import com.kuku9.goods.domain.search.service.SearchService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/keywords/all")
public class SearchController {

    private final SearchService searchService;

    @GetMapping
    public ResponseEntity<List<SearchResponse>> search(@RequestParam String searchName) {
        List<SearchResponse> response = searchService.search(searchName);

        return ResponseEntity.ok(response);
    }

}
