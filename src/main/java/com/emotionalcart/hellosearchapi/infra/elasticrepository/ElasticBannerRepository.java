package com.emotionalcart.hellosearchapi.infra.elasticrepository;

import com.emotionalcart.hellosearchapi.domain.elastic.banner.ElasticBanner;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ElasticBannerRepository extends ElasticsearchRepository<ElasticBanner, Long> {

}
