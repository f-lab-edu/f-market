package com.sorryisme.fmarket.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;
import org.springframework.data.elasticsearch.core.suggest.Completion;

@Setting(settingPath = "/elastic-settings.json")
@Mapping(mappingPath = "/elastic-mappings.json")
@Document(indexName = "products")
public class Products {

    @Id
    private String id;

    @Field(type = FieldType.Long)
    private Long productId;

    @CompletionField(analyzer = "autocomplete_index", searchAnalyzer = "autocomplete_search")
    private Completion productName;


    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Completion getProductName() {
        return productName;
    }

    public void setProductName(Completion productName) {
        this.productName = productName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
