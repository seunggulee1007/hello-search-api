package com.emotionalcart.hellosearchapi.elastic;

import lombok.Getter;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
public class ElasticProductOptionDetail {

    @Field(type = FieldType.Text, analyzer = "nori", searchAnalyzer = "nori", copyTo = "combined_field")
    private String optionDetailName;

    @Field(type = FieldType.Integer, copyTo = "combined_field")
    private Integer additionalPrice;

}
