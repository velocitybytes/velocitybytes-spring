package com.searchslice.model.search;

import com.searchslice.model.audit.DateAudit;
import lombok.*;
import org.elasticsearch.index.VersionType;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(
        name = "search",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {
                        "url"
                })
        }
)
@Document(
        indexName = "${app.elasticsearch.search.index}",
        versionType = VersionType.INTERNAL,
        createIndex = false
)
@Getter
@Setter
@RequiredArgsConstructor
public class Search extends DateAudit {

    @Id
    @org.springframework.data.annotation.Id
    @Column(name = "search_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "search_seq")
    @SequenceGenerator(name = "search_seq", allocationSize = 1)
    @Field(name = "searchId")
    private Long id;

    @NonNull
    @Column(name = "url", columnDefinition = "text")
    @Field(name = "url")
    private String url;

    @NonNull
    @Size(min = 3, max = 255)
    @Field(name = "title")
    private String title;

    @NonNull
    @Size(min = 3, max = 1024)
    @Column(name = "summary", columnDefinition = "text")
    @Field(name = "summary")
    private String summary;

    public Search() {
        super();
    }

    @Override
    public String toString() {
        return "Search{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                '}';
    }
}
