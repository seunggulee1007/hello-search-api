package com.emotionalcart.hellosearchapi;

import lombok.Getter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Document(indexName = "my_index")
@Getter
public class Product {

    private Long id;
    @Field(type = FieldType.Text, analyzer = "nori", searchAnalyzer = "nori", copyTo = "combined_field")
    private String name;
    @Field(type = FieldType.Text, analyzer = "nori", searchAnalyzer = "nori", copyTo = "combined_field")
    private String description;
    private List<String> tags;


}
