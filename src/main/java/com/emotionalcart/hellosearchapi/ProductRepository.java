package com.emotionalcart.hellosearchapi;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ProductRepository extends ElasticsearchRepository<Product, Long> {

    List<Product> findByNameContaining(String keyword);

}
