package com.emotionalcart.hellosearchapi.infra.batch;

import com.emotionalcart.hellosearchapi.application.order.OrderSaveService;
import com.emotionalcart.hellosearchapi.application.product.ProductService;
import com.emotionalcart.hellosearchapi.domain.rabbitmq.OrderSaveRequest;
import com.emotionalcart.hellosearchapi.infra.aggregator.OrderAggregator;
import com.emotionalcart.hellosearchapi.infra.aggregator.SalesAggregator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class BatchProcessor {

    private final SalesAggregator salesAggregator;
    private final OrderAggregator orderAggregator;
    private final OrderSaveService orderSaveService;
    private final ProductService productService;

    @Scheduled(fixedRate = 60000) // 1분마다 실행
    public void processSalesBatch() throws IOException {
        List<SalesCount> salesData = salesAggregator.getAggregatedSales();
        productService.syncProductSales(salesData.stream().map(SalesCount::toElastic).toList());
        salesAggregator.clearAggregatedSales();
        log.info("SalesCount Batch processed and saved to Elasticsearch!, sync count :: {}", salesData.size());
    }

    @Scheduled(fixedRate = 60000) // 1분마다 실행
    public void processOrdersBatch() throws IOException {
        List<OrderSaveRequest> orderSaveRequests = orderAggregator.getAggregatedOrderedList();
        orderSaveService.saveOrders(orderSaveRequests);
        orderAggregator.clearAggregatedOrderedList();
        log.info("OrderSaveRequest Batch processed and saved to Elasticsearch!, save count :: {}", orderSaveRequests.size());
    }

}