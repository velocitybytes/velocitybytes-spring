package com.searchslice.repository.impl;

import com.searchslice.model.elasticsearch.ElasticSearchItem;
import com.searchslice.model.search.Search;
import com.searchslice.repository.CustomSearchRepository;
import com.searchslice.util.Utils;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

import static com.searchslice.constant.Constants.SEARCH_SUMMARY;
import static com.searchslice.constant.Constants.SEARCH_TITLE;

/**
 * This class is the heart of elasticsearch operations and implements interface
 * @see CustomSearchRepository
 */
@Repository
public class CustomSearchRepositoryImpl implements CustomSearchRepository {

    private final ElasticsearchOperations elasticsearchOperations;

    /**
     * Elasticsearch index name
     */
    @Value("${app.elasticsearch.search.index}")
    private String indexName;

    @Autowired
    public CustomSearchRepositoryImpl(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    /**
     * This method handles search by using keyword with optional lookup in fieldNames
     * @param keyword search term
     * @param pageable pagination (page number and page size)
     * @param fieldNames fields to search in (title, summary)
     * @return search results for search term
     */
    @Override
    public Map<String, Object> searchByKeyword(String keyword, Pageable pageable, String... fieldNames) {

        List<String> searchFields = new ArrayList<>(Arrays.asList(fieldNames));

        short searchFieldsSize = (short) searchFields.size();

        if (keyword.length() >= 1 && keyword.length() < 3) {
            if (searchFieldsSize == 1 && Utils.containsValue(searchFields, SEARCH_TITLE)) {
                return getSearchMap(pageable.getPageNumber(), pageable.getPageSize(),
                        getFieldQueryBuilder(keyword, SEARCH_TITLE));
            } else if (searchFieldsSize == 1 && Utils.containsValue(searchFields, SEARCH_SUMMARY)) {
                return getSearchMap(pageable.getPageNumber(), pageable.getPageSize(),
                        getFieldQueryBuilder(keyword, SEARCH_SUMMARY));
            } else {
                return getSearchMap(pageable.getPageNumber(), pageable.getPageSize(), getAllFieldsQueryBuilder(keyword));
            }
        }
        QueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(keyword, fieldNames).fuzziness(Fuzziness.AUTO);
        return getSearchMap(pageable.getPageNumber(), pageable.getPageSize(), queryBuilder);
    }

    @Override
    public void indexSearchItem(ElasticSearchItem elasticSearchItem) {
        String docId = elasticSearchItem.getSearchId() + "";
        IndexQuery indexQuery = new IndexQueryBuilder()
                .withId(docId)
                .withObject(elasticSearchItem)
                .build();
        elasticsearchOperations.index(indexQuery, IndexCoordinates.of(indexName));
    }

    @Override
    public void indexSearchItems(List<Search> searchList) {
        List<IndexQuery> indexQueryList = new ArrayList<>(250);
        searchList.forEach(search -> {
            ElasticSearchItem searchItem = new ElasticSearchItem(
                    search.getId(),
                    search.getUrl(),
                    search.getTitle(),
                    search.getSummary()
            );
            IndexQuery indexQuery = new IndexQueryBuilder()
                    .withId(searchItem.getSearchId() + "")
                    .withObject(searchItem)
                    .build();
            indexQueryList.add(indexQuery);
            if (indexQueryList.size() == 250) {
                elasticsearchOperations.bulkIndex(indexQueryList, IndexCoordinates.of(indexName));
                indexQueryList.clear();
            }
        });
        if (indexQueryList.size() > 0 && indexQueryList.size() < 250) {
            elasticsearchOperations.bulkIndex(indexQueryList, IndexCoordinates.of(indexName));
            indexQueryList.clear();
        }
    }

    @Override
    public void deleteIndexedSearchItem(String docId) {
        elasticsearchOperations.delete(docId, IndexCoordinates.of(indexName));
    }

    /**
     * Returns search results map based on query builder
     * @param pageNumber page number
     * @param pageSize page size
     * @param queryBuilder query builder
     * @return search results as map
     */
    private Map<String, Object> getSearchMap(int pageNumber, int pageSize, QueryBuilder queryBuilder) {
        // to store final search results
        Map<String, Object> searchMap = new HashMap<>();
        // obtain search query
        Query searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withSearchType(SearchType.DEFAULT)
                .withPageable(PageRequest.of(pageNumber, pageSize))
                .build();
        // perform search operation
        SearchHits<ElasticSearchItem> searchHits = elasticsearchOperations.search(searchQuery, ElasticSearchItem.class,
                IndexCoordinates.of(indexName));
        // check if results are empty
        if (searchHits.isEmpty()) {
            searchMap.put("searchList", List.of());
            searchMap.put("searchCount", 0);
        } else {
            // traverse results and make a list
            List<ElasticSearchItem> searchResultsList = searchHits.getSearchHits()
                    .stream()
                    .parallel()
                    .map(SearchHit::getContent)
                    .collect(Collectors.toList());
            // search keyword max results hits (not result set size)
            searchMap.put("searchList", searchResultsList);
            searchMap.put("searchCount", searchHits.getTotalHits());
        }
        return searchMap;
    }

    /**
     * Builds query builder object based on wildcard search
     * @param keyword keyword
     * @param fieldName field name
     * @return QueryBuilder object
     */
    private QueryBuilder getFieldQueryBuilder(String keyword, String fieldName) {
        return QueryBuilders.boolQuery().must(QueryBuilders.wildcardQuery(fieldName, "*" + keyword + "*"));
    }

    /**
     * Builds query builder object based on wildcard search
     * @param keyword keyword
     * @return QueryBuilder object
     */
    private QueryBuilder getAllFieldsQueryBuilder(String keyword) {
        return QueryBuilders.boolQuery()
                .must(QueryBuilders.wildcardQuery(SEARCH_TITLE, "*" + keyword + "*"))
                .must(QueryBuilders.wildcardQuery(SEARCH_SUMMARY, "*" + keyword + "*"));
    }
}
