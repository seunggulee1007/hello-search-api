package com.emotionalcart.hellosearchapi.domain.elastic.product;

import com.emotionalcart.hellosearchapi.domain.entity.Product;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Document(indexName = "product_index")
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Setting(settingPath = "/elasticsearch/settings.json")
public class ElasticProduct {

    @Field(type = FieldType.Long)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @Field(type = FieldType.Text, analyzer = "nori", searchAnalyzer = "nori", copyTo = "combinedField")
    private String name;

    @Field(type = FieldType.Text, analyzer = "nori", searchAnalyzer = "nori", copyTo = "combinedField")
    private String description;

    @Field(type = FieldType.Integer)
    private Integer price;

    @Field(type = FieldType.Long)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long providerId;

    @Field(type = FieldType.Text, analyzer = "nori", searchAnalyzer = "nori", copyTo = "combinedField")
    private String providerName;

    @Field(type = FieldType.Long)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long categoryId;

    @Field(type = FieldType.Text, analyzer = "nori", searchAnalyzer = "nori", copyTo = "combinedField")
    private String categoryName;

    @Field(type = FieldType.Text, analyzer = "nori", searchAnalyzer = "nori")
    private String combinedField;

    @Field(type = FieldType.Integer)
    private Integer salesCount;

    @Field(type = FieldType.Boolean, name = "isDeleted")
    private boolean deleted;

    @Field(type = FieldType.Nested)
    private List<ElasticProductOption> options;

    @Field(type = FieldType.Date, format = {}, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt;

    public static ElasticProduct of(Product product) {
        ElasticProduct elasticProduct = new ElasticProduct();
        elasticProduct.id = product.getId();
        elasticProduct.name = product.getName();
        elasticProduct.description = product.getDescription();
        elasticProduct.price = product.getPrice();
        elasticProduct.providerId = product.getProvider().getId();
        elasticProduct.providerName = product.getProvider().getName();
        elasticProduct.categoryId = product.getCategory().getId();
        elasticProduct.categoryName = product.getCategory().getName();
        elasticProduct.options = ElasticProductOption.of(product.getOptions());
        elasticProduct.salesCount = 0;
        elasticProduct.deleted = product.isDeleted();
        elasticProduct.createdAt = product.getCreatedAt();
        return elasticProduct;
    }

}
