package com.emotionalcart.hellosearchapi.domain.elastic;

import com.emotionalcart.hellosearchapi.domain.elastic.product.ElasticProduct;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ElasticProductRepository extends ElasticsearchRepository<ElasticProduct, Long> {

    List<ElasticProduct> findByNameContaining(String keyword);

}
