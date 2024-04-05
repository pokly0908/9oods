package com.kuku9.goods.domain.search.service;

import com.kuku9.goods.domain.search.dto.SearchResponse;
import com.kuku9.goods.domain.search.repository.SearchQuery;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService{

    private final SearchQuery searchQuery;

    @Override
    public List<SearchResponse> search(String searchName) {
        return searchQuery.search(searchName);
    }

}
