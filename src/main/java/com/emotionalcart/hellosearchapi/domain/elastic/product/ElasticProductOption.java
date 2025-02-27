package com.emotionalcart.hellosearchapi.domain.elastic.product;

import com.emotionalcart.hellosearchapi.domain.entity.ProductOption;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ElasticProductOption {

    @Field(type = FieldType.Long)
    private Long id;

    @Field(type = FieldType.Text, analyzer = "nori", searchAnalyzer = "nori")
    private String optionName;

    @Field(type = FieldType.Nested)
    private List<ElasticProductOptionDetail> details;

    public static List<ElasticProductOption> of(List<ProductOption> options) {

        return options.stream()
                .map(option -> {
                    ElasticProductOption elasticProductOption = new ElasticProductOption();
                    elasticProductOption.id = option.getId();
                    elasticProductOption.optionName = option.getName();
                    elasticProductOption.details = ElasticProductOptionDetail.of(option.getDetails());
                    return elasticProductOption;
                })
                .toList();
    }

}
