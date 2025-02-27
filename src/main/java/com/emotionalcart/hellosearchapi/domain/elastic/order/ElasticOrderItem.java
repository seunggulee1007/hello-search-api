package com.emotionalcart.hellosearchapi.domain.elastic.order;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ElasticOrderItem {

    @Field(type = FieldType.Long)
    private Long id;

    @Field(type = FieldType.Long)
    private Long productId;

    @Field(type = FieldType.Long)
    private Long categoryId;

    @Field(type = FieldType.Text, analyzer = "nori", searchAnalyzer = "nori")
    private String productName;

    @Field(type = FieldType.Text, analyzer = "nori", searchAnalyzer = "nori")
    private String categoryName;

    @Field(type = FieldType.Integer)
    private Integer price;

    private List<ElasticOrderItemOption> itemOptions;

}
