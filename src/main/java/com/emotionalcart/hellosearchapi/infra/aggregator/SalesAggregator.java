package com.emotionalcart.hellosearchapi.infra.aggregator;

import com.emotionalcart.hellosearchapi.domain.rabbitmq.OrderSaveRequest;
import com.emotionalcart.hellosearchapi.infra.batch.SalesCount;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalesAggregator {

    private final HashOperations<String, String, Integer> hashOperations;
    private static final String SALES_KEY = "sales_agg";

    public SalesAggregator(@Qualifier("redisSalesTemplate") RedisTemplate<String, Integer> redisSalesTemplate) {
        this.hashOperations = redisSalesTemplate.opsForHash();
    }

    public void aggregate(OrderSaveRequest orderSaveRequest) {
        for (OrderSaveRequest.OrderItem item : orderSaveRequest.getItems()) {
            hashOperations.increment(SALES_KEY, String.valueOf(item.getProductId()), item.getQuantity());
        }
    }

    public List<SalesCount> getAggregatedSales() {
        return hashOperations.entries(SALES_KEY).entrySet().stream().map(m -> SalesCount.of(m.getKey(), m.getValue())).toList();
    }

    public void clearAggregatedSales() {
        hashOperations.getOperations().delete(SALES_KEY);
    }

}