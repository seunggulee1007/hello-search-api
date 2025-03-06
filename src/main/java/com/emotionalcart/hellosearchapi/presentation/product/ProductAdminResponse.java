package com.emotionalcart.hellosearchapi.presentation.product;

import com.emotionalcart.hellosearchapi.domain.elastic.product.ElasticProduct;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class ProductAdminResponse {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long productId;

    private String highlight;

    public static ProductAdminResponse of(ElasticProduct source, Map<String, List<String>> highlight) {
        ProductAdminResponse response = new ProductAdminResponse();
        response.productId = source.getId();
        response.highlight = highlight.get("combinedField") != null ? String.join(",", highlight.get("combinedField")) : null;
        return response;
    }

}
