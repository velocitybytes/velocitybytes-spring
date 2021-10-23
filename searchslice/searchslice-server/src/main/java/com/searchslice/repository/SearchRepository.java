package com.searchslice.repository;

import com.searchslice.model.search.Search;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SearchRepository extends JpaRepository<Search, Long> {

}
