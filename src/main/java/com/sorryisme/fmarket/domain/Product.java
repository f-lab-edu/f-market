package com.sorryisme.fmarket.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;
import org.springframework.data.elasticsearch.core.suggest.Completion;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    @Id
    private Long id;

    @CompletionField(analyzer = "autocomplete_index", searchAnalyzer = "autocomplete_search")
    private Completion productName;

    private String description;
    private String thumbnail;
    private Integer majorCategory;
    private Integer subcategory;
    private String catalog;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

}
