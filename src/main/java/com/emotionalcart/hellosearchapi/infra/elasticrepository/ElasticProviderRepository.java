package com.emotionalcart.hellosearchapi.infra.elasticrepository;

import com.emotionalcart.hellosearchapi.domain.elastic.provider.ElasticProvider;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ElasticProviderRepository extends ElasticsearchRepository<ElasticProvider, Long> {

}
