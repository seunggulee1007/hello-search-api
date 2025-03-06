package com.emotionalcart.hellosearchapi.domain.entity;

import com.emotionalcart.hellosearchapi.domain.elastic.banner.ElasticBanner;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
public class Banner extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private Integer bannerOrder;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String iconPath;

    public ElasticBanner mapToElasticBanner() {
        return ElasticBanner.builder()
            .id(id)
            .title(title)
            .description(description)
            .bannerOrder(bannerOrder)
            .startDate(startDate)
            .endDate(endDate)
            .iconPath(iconPath)
            .createdAt(getCreatedAt())
            .build();
    }

}
