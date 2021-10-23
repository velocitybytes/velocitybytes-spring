package com.searchslice.repository;

import com.searchslice.model.elasticsearch.ElasticSearchItem;
import com.searchslice.model.search.Search;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface CustomSearchRepository {

    /**
     * This method handles search by using keyword with optional lookup in fieldNames
     * @param keyword search term
     * @param pageable pagination (page number and page size)
     * @param fieldNames fields to search in (title, summary)
     * @return search results for search term
     */
    Map<String, Object> searchByKeyword(String keyword, Pageable pageable, String... fieldNames);

    void indexSearchItem(ElasticSearchItem elasticSearchItem);

    void indexSearchItems(List<Search> searchList);

    void deleteIndexedSearchItem(String docId);
}
