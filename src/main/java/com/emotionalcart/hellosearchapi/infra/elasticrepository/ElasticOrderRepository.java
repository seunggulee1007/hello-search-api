package com.emotionalcart.hellosearchapi.infra.elasticrepository;

import com.emotionalcart.hellosearchapi.domain.elastic.order.ElasticOrder;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ElasticOrderRepository extends ElasticsearchRepository<ElasticOrder, Long> {

}
