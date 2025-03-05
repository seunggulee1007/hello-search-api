package com.emotionalcart.hellosearchapi.domain.elastic.order;

import lombok.Getter;

@Getter
public class ElasticSalesCount {

    private String id;

    private Integer count;

    public static ElasticSalesCount of(String id, Integer count) {
        ElasticSalesCount salesCount = new ElasticSalesCount();
        salesCount.id = id;
        salesCount.count = count;
        return salesCount;
    }

}
