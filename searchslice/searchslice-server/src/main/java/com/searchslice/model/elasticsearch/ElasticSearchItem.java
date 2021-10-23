package com.searchslice.model.elasticsearch;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.elasticsearch.index.VersionType;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(
        indexName = "${app.elasticsearch.search.index}",
        versionType = VersionType.INTERNAL,
        createIndex = false
)
@Setting(settingPath = "es_search_settings.json")
public class ElasticSearchItem {

    @Id
    @Field(name = "searchId")
    private Long searchId;

    @Field(name = "url")
    private String url;

    @MultiField(
            mainField = @Field(
                    name = "title",
                    type = FieldType.Text,
                    fielddata = true,
                    analyzer = "autocomplete",
                    searchAnalyzer = "autocomplete_search"
            ),
            otherFields = {
                    @InnerField(
                            suffix = "edge_ngram",
                            type = FieldType.Text,
                            analyzer = "edge_ngram_analyzer",
                            searchAnalyzer = "keyword_analyzer"
                    ),
                    @InnerField(
                            suffix = "ngram",
                            type = FieldType.Text,
                            analyzer = "ngram_analyzer",
                            searchAnalyzer = "keyword_analyzer"
                    ),
                    @InnerField(
                            suffix = "english",
                            type = FieldType.Text,
                            analyzer = "english_analyzer"
                    ),
            }
    )
    private String title;

    @MultiField(
            mainField = @Field(
                    name = "summary",
                    type = FieldType.Text,
                    fielddata = true,
                    analyzer = "autocomplete",
                    searchAnalyzer = "autocomplete_search"
            ),
            otherFields = {
                    @InnerField(
                            suffix = "edge_ngram",
                            type = FieldType.Text,
                            analyzer = "edge_ngram_analyzer",
                            searchAnalyzer = "keyword_analyzer"
                    ),
                    @InnerField(
                            suffix = "ngram",
                            type = FieldType.Text,
                            analyzer = "ngram_analyzer",
                            searchAnalyzer = "keyword_analyzer"
                    ),
                    @InnerField(
                            suffix = "english",
                            type = FieldType.Text,
                            analyzer = "english_analyzer"
                    ),
            }
    )
    private String summary;
    
}
