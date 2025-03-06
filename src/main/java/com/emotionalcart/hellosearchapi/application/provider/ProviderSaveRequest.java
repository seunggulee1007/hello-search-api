package com.emotionalcart.hellosearchapi.application.provider;

import com.emotionalcart.hellosearchapi.domain.elastic.provider.ElasticProvider;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ProviderSaveRequest {

    private Long id;

    private String name;

    private String description;

    private String combinedField;

    private LocalDateTime createdAt;

    public ElasticProvider mapToElasticProvider() {
        return ElasticProvider.builder()
            .id(id)
            .name(name)
            .description(description)
            .combinedField(combinedField)
            .createdAt(createdAt)
            .build();
    }

}
