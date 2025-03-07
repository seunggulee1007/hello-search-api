package com.emotionalcart.hellosearchapi.application.product;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.UpdateRequest;
import co.elastic.clients.elasticsearch.core.UpdateResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductDeleteService {

    private final ElasticsearchClient esClient;

    public void deleteProduct(Long productId) throws IOException {
        UpdateRequest<Object, Object> updateRequest = UpdateRequest.of(u -> u
            .index("product_index")
            .id(String.valueOf(productId))
            .docAsUpsert(true)
            .doc("{\"isDeleted\": true}"));
        UpdateResponse<Object> response = esClient.update(updateRequest, Object.class);
        log.info("product deleted : {}, result : {}", productId, response.result());
    }

}
