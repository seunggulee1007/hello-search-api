package com.emotionalcart.hellosearchapi.application.banner;

import com.emotionalcart.hellosearchapi.domain.elastic.banner.ElasticBanner;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class BannerSearchResponse {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long bannerId;

    private String highlight;

    public static BannerSearchResponse of(ElasticBanner source, Map<String, List<String>> highlight) {
        BannerSearchResponse response = new BannerSearchResponse();
        response.bannerId = source.getId();
        response.highlight = highlight.get("combinedField") != null ? String.join(",", highlight.get("combinedField")) : null;
        return response;
    }

}
