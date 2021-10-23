package com.searchslice;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.searchslice.model.search.Search;
import com.searchslice.service.impl.SearchServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.TimeZone;

@Log4j2
@SpringBootApplication
@EntityScan(basePackageClasses = {
        SearchsliceServerApplication.class, Jsr310JpaConverters.class
})
public class SearchsliceServerApplication {

    @PostConstruct
    void init() {
        log.debug("Setting timezone to UTC");
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    public static void main(String[] args) {
        SpringApplication.run(SearchsliceServerApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(SearchServiceImpl searchServiceImpl) {
        return args -> {
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<Search>> typeReference = new TypeReference<>(){};
            InputStream inputStream = TypeReference.class.getResourceAsStream("/json/search.json");
            try {
                List<Search> searches = mapper.readValue(inputStream, typeReference);
                searchServiceImpl.createSearches(searches);
                log.info("Recipes saved to database.");
            } catch (IOException e) {
                log.error("Unable to save recipes to database: {}", e.getMessage());
            }
        };
    }
}
