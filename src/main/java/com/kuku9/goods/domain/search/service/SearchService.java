package com.kuku9.goods.domain.search.service;

import com.kuku9.goods.domain.search.dto.SearchResponse;
import java.util.List;

public interface SearchService {

    /**
     * 
     * @param searchName 검색어
     * @return 검색어와 일치한 조회 데이터
     */
    List<SearchResponse> search(String searchName);
    
}
