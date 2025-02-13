package com.emotionalcart.hellosearchapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.elasticsearch.support.HttpHeaders;

@Configuration
@EnableElasticsearchRepositories
public class ElasticSearchConfig extends ElasticsearchConfiguration {

    @Value("${spring.elasticsearch.username}")
    private String username;

    @Value("${spring.elasticsearch.password}")
    private String password;

    @Value("${spring.elasticsearch.api-key}")
    private String apiKey;

    @Value("${spring.elasticsearch.host}")
    private String host;

    @Value("${spring.elasticsearch.fingerprint}")
    private String fingerprint;

    @Override
    public ClientConfiguration clientConfiguration() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "ApiKey " + apiKey);
        return ClientConfiguration.builder()
            .connectedTo(host)
            .usingSsl(fingerprint)
            .withDefaultHeaders(headers)
            .withBasicAuth(username, password)
            .build();
    }


}