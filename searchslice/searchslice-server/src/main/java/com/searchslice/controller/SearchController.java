package com.searchslice.controller;

import com.searchslice.exception.SearchRequestCreationException;
import com.searchslice.payload.ApiResponse;
import com.searchslice.payload.SearchRequest;
import com.searchslice.service.SearchService;
import lombok.NonNull;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/search")
public class SearchController {

    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createSearchItem(@RequestBody SearchRequest searchRequest) {
        return searchService.createSearch(searchRequest)
                .map(searchItem -> ResponseEntity.ok(new ApiResponse(true, "Search item created successfully!")))
                .orElseThrow(() -> new SearchRequestCreationException(searchRequest.getUrl(),
                        "Missing search object in database"));
    }

    @GetMapping("/keyword/{keyword}")
    public ResponseEntity<?> searchByKeyword(Exception exception, @PathVariable("keyword") final String keyword,
                                             @RequestParam @NonNull Map<String, String> params) {
        // pagination
        Pageable pageable = PageRequest.of(Integer.parseInt(params.get("pageNum")),
                Integer.parseInt(params.get("pageSize")));
        // remove pageNum and pageSize values after conversion of request param values to array (not needed)
        Object[] fields = ArrayUtils.removeElement(params.values().toArray(), params.get("pageNum"));
        fields = ArrayUtils.removeElement(fields, params.get("pageSize"));

        // Get final search results as a map
        Map<String, Object> searchMap = searchService.searchByKeyword(keyword, pageable,
                Arrays.copyOf(fields, fields.length, String[].class));

        if (exception instanceof DataAccessResourceFailureException) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } else if (exception instanceof NullPointerException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return ResponseEntity.ok(new ApiResponse(true, searchMap));
        }
    }

}
