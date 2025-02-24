package com.emotionalcart.hellosearchapi.elastic;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "spring.data.elasticsearch.index")
public class ElasticIndexProperties {

    private String product;

}
