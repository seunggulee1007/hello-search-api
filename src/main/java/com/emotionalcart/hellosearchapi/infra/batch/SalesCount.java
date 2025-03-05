package com.emotionalcart.hellosearchapi.infra.batch;

import com.emotionalcart.hellosearchapi.domain.elastic.order.ElasticSalesCount;
import lombok.Getter;

@Getter
public class SalesCount {

    private String id;

    private Integer count;

    public static SalesCount of(String id, Integer count) {
        SalesCount salesCount = new SalesCount();
        salesCount.id = id;
        salesCount.count = count;
        return salesCount;
    }

    public ElasticSalesCount toElastic() {
        return ElasticSalesCount.of(id, count);
    }

}
