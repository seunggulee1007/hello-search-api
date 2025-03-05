package com.emotionalcart.hellosearchapi.infra.aggregator;

import com.emotionalcart.hellosearchapi.domain.rabbitmq.OrderSaveRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderAggregator {

    private final HashOperations<String, String, OrderSaveRequest> hashOperations;
    private static final String ORDERED_KEY = "ordered";

    public OrderAggregator(@Qualifier("redisOrderTemplate") RedisTemplate<String, OrderSaveRequest> redisOrderTemplate) {
        this.hashOperations = redisOrderTemplate.opsForHash();
    }

    public void aggregate(OrderSaveRequest orderSaveRequest) {
        hashOperations.put(ORDERED_KEY, String.valueOf(orderSaveRequest.getId()), orderSaveRequest);
    }

    public List<OrderSaveRequest> getAggregatedOrderedList() {
        return hashOperations.entries(ORDERED_KEY).values().stream().toList();
    }

    public void clearAggregatedOrderedList() {
        hashOperations.getOperations().delete(ORDERED_KEY);
    }

}