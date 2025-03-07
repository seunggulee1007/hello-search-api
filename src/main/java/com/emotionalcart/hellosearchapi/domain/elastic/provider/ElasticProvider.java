package com.emotionalcart.hellosearchapi.domain.elastic.provider;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Getter
@Builder
@Document(indexName = "provider_index")
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ElasticProvider {

    private Long id;

    @Field(type = FieldType.Boolean, name = "isDeleted")
    private boolean deleted;

    @Field(type = FieldType.Text, analyzer = "nori", searchAnalyzer = "nori", copyTo = "combinedField")
    private String name;

    @Field(type = FieldType.Text, analyzer = "nori", searchAnalyzer = "nori", copyTo = "combinedField")
    private String description;

    @Field(type = FieldType.Text, analyzer = "nori", searchAnalyzer = "nori")
    private String combinedField;

    @Field(type = FieldType.Date, format = {}, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt;

}
