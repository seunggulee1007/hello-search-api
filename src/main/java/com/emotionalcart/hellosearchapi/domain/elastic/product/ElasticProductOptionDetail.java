package com.emotionalcart.hellosearchapi.domain.elastic.product;

import com.emotionalcart.hellosearchapi.domain.entity.ProductOptionDetail;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ElasticProductOptionDetail {

    @Field(type = FieldType.Long)
    private Long id;

    @Field(type = FieldType.Text, analyzer = "nori", searchAnalyzer = "nori")
    private String optionDetailName;

    @Field(type = FieldType.Integer)
    private Integer additionalPrice;

    public static List<ElasticProductOptionDetail> of(List<ProductOptionDetail> details) {

        return details.stream()
                .map(detail -> {
                    ElasticProductOptionDetail elasticProductOptionDetail = new ElasticProductOptionDetail();
                    elasticProductOptionDetail.id = detail.getId();
                    elasticProductOptionDetail.optionDetailName = detail.getValue();
                    elasticProductOptionDetail.additionalPrice = detail.getAdditionalPrice();
                    return elasticProductOptionDetail;
                })
                .toList();
    }

}
