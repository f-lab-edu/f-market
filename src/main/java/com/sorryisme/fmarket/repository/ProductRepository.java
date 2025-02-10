package com.sorryisme.fmarket.repository;

import com.sorryisme.fmarket.domain.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ProductRepository extends ElasticsearchRepository<Product, String> {
    List<Product> searchByProductName(String productName);
}