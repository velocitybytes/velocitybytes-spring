package com.searchslice.service.impl;

import com.searchslice.model.elasticsearch.ElasticSearchItem;
import com.searchslice.model.search.Search;
import com.searchslice.payload.SearchRequest;
import com.searchslice.repository.CustomSearchRepository;
import com.searchslice.repository.SearchRepository;
import com.searchslice.service.SearchService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SearchServiceImpl implements SearchService {

    private final SearchRepository searchRepository;
    private final CustomSearchRepository customSearchRepository;

    @Autowired
    public SearchServiceImpl(SearchRepository searchRepository, CustomSearchRepository customSearchRepository) {
        this.searchRepository = searchRepository;
        this.customSearchRepository = customSearchRepository;
    }

    @Override
    public Map<String, Object> searchByKeyword(String keyword, Pageable pageable, String... fieldNames) {
        return customSearchRepository.searchByKeyword(keyword, pageable, fieldNames);
    }

    @Override
    public Optional<Search> createSearch(@NotNull SearchRequest searchRequest) {
        Search search = new Search();
        search.setUrl(searchRequest.getUrl());
        search.setTitle(searchRequest.getTitle());
        search.setSummary(searchRequest.getSummary());
        Search createdSearchItem = searchRepository.save(search);
        ElasticSearchItem searchItem = new ElasticSearchItem(
                createdSearchItem.getId(),
                createdSearchItem.getUrl(),
                createdSearchItem.getTitle(),
                createdSearchItem.getSummary()
        );
        customSearchRepository.indexSearchItem(searchItem);
        return Optional.of(createdSearchItem);
    }

    public void createSearches(List<Search> searches) {
        List<Search> savedSearchList = searchRepository.saveAll(searches);
        customSearchRepository.indexSearchItems(savedSearchList);
    }
}
