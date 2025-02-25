package com.emotionalcart.hellosearchapi.elastic;

import com.emotionalcart.hellosearchapi.entity.ProductOption;
import lombok.Getter;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Getter
public class ElasticProductOption {

    @Field(type = FieldType.Text, analyzer = "nori", searchAnalyzer = "nori")
    private String optionName;

    @Field(type = FieldType.Nested)
    private List<ElasticProductOptionDetail> details;

    public static List<ElasticProductOption> of(List<ProductOption> options) {

        return options.stream()
                .map(option -> {
                    ElasticProductOption elasticProductOption = new ElasticProductOption();
                    elasticProductOption.optionName = option.getName();
                    elasticProductOption.details = ElasticProductOptionDetail.of(option.getDetails());
                    return elasticProductOption;
                })
                .toList();
    }

}
