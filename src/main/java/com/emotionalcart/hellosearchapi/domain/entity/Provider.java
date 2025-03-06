package com.emotionalcart.hellosearchapi.domain.entity;

import com.emotionalcart.hellosearchapi.domain.elastic.provider.ElasticProvider;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Provider extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    public ElasticProvider mapToElastic() {
        return ElasticProvider.builder()
            .id(id)
            .name(name)
            .description(description)
            .build();
    }

}
