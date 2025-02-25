package com.emotionalcart.hellosearchapi.elastic;

import com.emotionalcart.hellosearchapi.entity.Product;
import lombok.Getter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.util.List;

@Document(indexName = "product_index")
@Setting(settingPath = "/elasticsearch/settings.json")
@Getter
public class ElasticProduct {

    private Long id;

    @Field(type = FieldType.Text, analyzer = "nori", searchAnalyzer = "nori", copyTo = "combined_field")
    private String name;

    @Field(type = FieldType.Text, analyzer = "nori", searchAnalyzer = "nori", copyTo = "combined_field")
    private String description;

    @Field(type = FieldType.Integer)
    private Integer price;

    @Field(type = FieldType.Long)
    private Long providerId;

    @Field(type = FieldType.Text, analyzer = "nori", searchAnalyzer = "nori", copyTo = "combined_field")
    private String providerName;

    @Field(type = FieldType.Long)
    private Long categoryId;

    @Field(type = FieldType.Text, analyzer = "nori", searchAnalyzer = "nori", copyTo = "combined_field")
    private String categoryName;

    @Field(type = FieldType.Text, analyzer = "nori", searchAnalyzer = "nori")
    private String combinedField;

    @Field(type = FieldType.Nested)
    private List<ElasticProductOption> options;

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
        return elasticProduct;
    }

}
