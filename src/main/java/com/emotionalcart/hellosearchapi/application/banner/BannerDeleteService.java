package com.emotionalcart.hellosearchapi.application.banner;

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
public class BannerDeleteService {

    private final ElasticsearchClient esClient;

    public void deleteBanner(Long bannerId) throws IOException {
        UpdateRequest<Object, Object> updateRequest = UpdateRequest.of(u -> u
            .index("banner_index")
            .id(String.valueOf(bannerId))
            .docAsUpsert(true)
            .doc("{\"isDeleted\": true}"));
        UpdateResponse<Object> response = esClient.update(updateRequest, Object.class);
        log.info("product deleted : {}, result : {}", bannerId, response.result());
    }

}
