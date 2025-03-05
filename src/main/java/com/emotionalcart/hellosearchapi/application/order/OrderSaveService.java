package com.emotionalcart.hellosearchapi.application.order;

import com.emotionalcart.hellosearchapi.domain.elastic.order.ElasticOrder;
import com.emotionalcart.hellosearchapi.domain.rabbitmq.OrderSaveRequest;
import com.emotionalcart.hellosearchapi.infra.elasticrepository.ElasticOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderSaveService {

    private final ElasticOrderRepository elasticOrderRepository;

    public void save(ElasticOrder elasticOrder) {
        elasticOrderRepository.save(elasticOrder);
    }

    public void saveOrders(List<OrderSaveRequest> orderSaveRequests) {
        List<ElasticOrder> elasticOrders = orderSaveRequests.stream()
                .map(OrderSaveRequest::mapToElasticOrder)
                .toList();
        elasticOrderRepository.saveAll(elasticOrders);
    }

}
