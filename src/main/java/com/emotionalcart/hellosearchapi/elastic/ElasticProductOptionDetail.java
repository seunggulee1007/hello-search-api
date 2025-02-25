package com.emotionalcart.hellosearchapi.elastic;

import com.emotionalcart.hellosearchapi.entity.ProductOptionDetail;
import lombok.Getter;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Getter
public class ElasticProductOptionDetail {

    @Field(type = FieldType.Text, analyzer = "nori", searchAnalyzer = "nori")
    private String optionDetailName;

    @Field(type = FieldType.Integer)
    private Integer additionalPrice;

    public static List<ElasticProductOptionDetail> of(List<ProductOptionDetail> details) {

        return details.stream()
                .map(detail -> {
                    ElasticProductOptionDetail elasticProductOptionDetail = new ElasticProductOptionDetail();
                    elasticProductOptionDetail.optionDetailName = detail.getValue();
                    elasticProductOptionDetail.additionalPrice = detail.getAdditionalPrice();
                    return elasticProductOptionDetail;
                })
                .toList();
    }

}
