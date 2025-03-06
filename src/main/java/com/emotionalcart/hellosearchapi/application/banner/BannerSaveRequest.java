package com.emotionalcart.hellosearchapi.application.banner;

import com.emotionalcart.hellosearchapi.domain.elastic.banner.ElasticBanner;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BannerSaveRequest {

    private Long id;

    private boolean deleted;

    private String combinedField;

    private String title;

    private String description;

    private int bannerOrder;

    private LocalDateTime createdAt;

    public ElasticBanner mapToElasticBanner() {
        return ElasticBanner.builder()
            .id(id)
            .deleted(deleted)
            .combinedField(combinedField)
            .title(title)
            .description(description)
            .bannerOrder(bannerOrder)
            .createdAt(createdAt)
            .build();
    }

}
