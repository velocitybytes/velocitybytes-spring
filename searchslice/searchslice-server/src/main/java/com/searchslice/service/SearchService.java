package com.searchslice.service;

import com.searchslice.model.search.Search;
import com.searchslice.payload.SearchRequest;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.Optional;

public interface SearchService {

    /**
     * This method handles search by using keyword with optional lookup in fieldNames
     * @param keyword search term
     * @param pageable pagination (page number and page size)
     * @param fieldNames fields to search in (title, summary)
     * @return search results for search term
     */
    Map<String, Object> searchByKeyword(String keyword, Pageable pageable, String... fieldNames);

    Optional<Search> createSearch(SearchRequest searchRequest);
}
