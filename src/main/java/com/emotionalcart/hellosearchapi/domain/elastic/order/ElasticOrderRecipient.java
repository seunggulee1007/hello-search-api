package com.emotionalcart.hellosearchapi.domain.elastic.order;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ElasticOrderRecipient {

    @Field(type = FieldType.Long)
    private Long id;

    @Field(type = FieldType.Text, analyzer = "nori", searchAnalyzer = "nori", copyTo = "combined_field")
    private String recipientName;

    private String recipientPhone;

    private String postalCode;

    @Field(type = FieldType.Text, analyzer = "nori", searchAnalyzer = "nori", copyTo = "combined_field")
    private String defaultAddress;

    @Field(type = FieldType.Text, analyzer = "nori", searchAnalyzer = "nori", copyTo = "combined_field")
    private String detailAddress;

    @Field(type = FieldType.Text, analyzer = "nori", searchAnalyzer = "nori", copyTo = "combined_field")
    private String orderRequirement;

    @Field(type = FieldType.Text, analyzer = "nori", searchAnalyzer = "nori", copyTo = "combined_field")
    private String deliveryRequirement;

}
