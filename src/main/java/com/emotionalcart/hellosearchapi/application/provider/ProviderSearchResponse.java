package com.emotionalcart.hellosearchapi.application.provider;

import com.emotionalcart.hellosearchapi.domain.elastic.provider.ElasticProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class ProviderSearchResponse {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long providerId;

    private String highlight;

    public static ProviderSearchResponse of(ElasticProvider source, Map<String, List<String>> highlight) {
        ProviderSearchResponse response = new ProviderSearchResponse();
        response.providerId = source.getId();
        response.highlight = highlight.get("combinedField") != null ? String.join(",", highlight.get("combinedField")) : null;
        return response;
    }

}
