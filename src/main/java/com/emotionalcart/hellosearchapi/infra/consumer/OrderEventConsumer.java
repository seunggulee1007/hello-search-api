package com.emotionalcart.hellosearchapi.infra.consumer;

import com.emotionalcart.hellosearchapi.domain.rabbitmq.OrderSaveRequest;
import com.emotionalcart.hellosearchapi.infra.aggregator.OrderAggregator;
import com.emotionalcart.hellosearchapi.infra.aggregator.SalesAggregator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventConsumer {

    private final SalesAggregator salesAggregator;
    private final OrderAggregator orderAggregator;

    @RabbitListener(queues = "order-queue")
    public void consumeSalesEvent(OrderSaveRequest orderSaveRequest) {
        salesAggregator.aggregate(orderSaveRequest);
        orderAggregator.aggregate(orderSaveRequest);
        log.info("Consumed: {}", orderSaveRequest);
    }

}