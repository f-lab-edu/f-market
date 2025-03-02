package com.sorryisme.fmarket.repository;

import com.sorryisme.fmarket.document.Products;
import com.sorryisme.fmarket.domain.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ProductRepository extends ElasticsearchRepository<Products, String> {
    List<Products> searchProductsByProductName(String productName);
}