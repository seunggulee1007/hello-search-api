package com.emotionalcart.hellosearchapi.elastic;

import lombok.Getter;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Getter
public class ElasticProductOption {

    @Field(type = FieldType.Text, analyzer = "nori", searchAnalyzer = "nori", copyTo = "combined_field")
    private String optionName;

    @Field(type = FieldType.Nested)
    private List<ElasticProductOptionDetail> details;

}
