package com.emotionalcart.hellosearchapi.domain.elastic.order;

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
import org.springframework.data.elasticsearch.annotations.Setting;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@Document(indexName = "order_index")
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Setting(settingPath = "/elasticsearch/order_settings.json")
public class ElasticOrder {

    private Long id;

    @Field(type = FieldType.Double)
    private double totalPrice;

    @Field(type = FieldType.Date, format = {}, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime orderAt;

    @Field(type = FieldType.Long)
    private Long orderMemberId;

    @Field(type = FieldType.Text, analyzer = "nori", searchAnalyzer = "nori", copyTo = "combinedField")
    private String orderMemberName;

    @Field(type = FieldType.Text, analyzer = "nori", searchAnalyzer = "nori", copyTo = "combinedField")
    private String orderStatus;

    @Field(type = FieldType.Text, analyzer = "nori", searchAnalyzer = "nori")
    private String combinedField;

    @Field(type = FieldType.Nested)
    private List<ElasticOrderItem> items;

    @Field(type = FieldType.Nested)
    private ElasticOrderRecipient recipient;

}
