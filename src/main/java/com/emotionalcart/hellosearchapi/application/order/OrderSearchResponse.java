package com.emotionalcart.hellosearchapi.application.order;

import com.emotionalcart.hellosearchapi.domain.elastic.order.ElasticOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class OrderSearchResponse {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long orderId;

    private String highlight;

    public static OrderSearchResponse of(ElasticOrder source, Map<String, List<String>> highlight) {
        OrderSearchResponse response = new OrderSearchResponse();
        response.orderId = source.getId();
        response.highlight = highlight.get("combinedField") != null ? String.join(",", highlight.get("combinedField")) : null;
        return response;
    }

}
