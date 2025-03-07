package com.emotionalcart.hellosearchapi.application.provider;

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
public class ProviderDeleteService {

    private final ElasticsearchClient esClient;

    public void deleteProvider(Long providerId) throws IOException {
        UpdateRequest<Object, Object> updateRequest = UpdateRequest.of(u -> u
            .index("provider_index")
            .id(String.valueOf(providerId))
            .docAsUpsert(true)
            .doc("{\"isDeleted\": true}"));
        UpdateResponse<Object> response = esClient.update(updateRequest, Object.class);
        log.info("provider deleted : {}, result : {}", providerId, response.result());
    }

}
