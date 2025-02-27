package com.emotionalcart.hellosearchapi.domain.elastic.order;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ElasticOrderItemOption {

    @Field(type = FieldType.Long)
    private Long id;

    @Field(type = FieldType.Long)
    private Long optionId;

    @Field(type = FieldType.Text, analyzer = "nori", searchAnalyzer = "nori")
    private String optionName;

    @Field(type = FieldType.Long)
    private Long optionDetailId;

    @Field(type = FieldType.Text, analyzer = "nori", searchAnalyzer = "nori")
    private String optionDetailName;

}
