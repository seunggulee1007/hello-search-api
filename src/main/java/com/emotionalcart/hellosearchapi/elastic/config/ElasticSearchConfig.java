package com.emotionalcart.hellosearchapi.elastic.config;

import com.emotionalcart.hellosearchapi.elastic.properties.ElasticProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.elasticsearch.support.HttpHeaders;

@Configuration
@EnableElasticsearchRepositories
@RequiredArgsConstructor
public class ElasticSearchConfig extends ElasticsearchConfiguration {

    private final ElasticProperties elasticProperties;

//
    @Override
    public ClientConfiguration clientConfiguration() {
        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "ApiKey " + apiKey);
        return ClientConfiguration.builder()
            .connectedTo(elasticProperties.getHost())
            .usingSsl(elasticProperties.getFingerprint())
            .withBasicAuth(elasticProperties.getUsername(), elasticProperties.getPassword())
            .build();
    }


}