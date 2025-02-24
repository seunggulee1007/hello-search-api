package com.emotionalcart.hellosearchapi.elastic;

import lombok.Getter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Document(indexName = "product_index")
@Getter
public class ElasticProduct {

    private Long id;
    @Field(type = FieldType.Text, analyzer = "nori", searchAnalyzer = "nori", copyTo = "combined_field")
    private String name;
    @Field(type = FieldType.Text, analyzer = "nori", searchAnalyzer = "nori", copyTo = "combined_field")
    private String description;
    @Field(type = FieldType.Integer, copyTo = "combined_field")
    private Integer price;
    @Field(type = FieldType.Long, copyTo = "combined_field")
    private Long providerId;
    @Field(type = FieldType.Text, analyzer = "nori", searchAnalyzer = "nori", copyTo = "combined_field")
    private String categoryName;
    @Field(type = FieldType.Nested)
    private List<ElasticProductOption> options;

}
