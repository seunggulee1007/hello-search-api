package com.emotionalcart.hellosearchapi.elastic.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "spring.elasticsearch")
public class ElasticProperties extends ElasticsearchProperties {

    private String host;
    private String fingerprint;

}
