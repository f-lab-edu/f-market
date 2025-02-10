package com.sorryisme.fmarket.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(indexName = "products")
public class Product {

    @Id
    private Long id;

    @Field(type = FieldType.Text, analyzer = "")
    private String productName;

    private String description;
    private String thumbnail;
    private Integer majorCategory;
    private Integer subcategory;
    private String catalog;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

}
