package com.emotionalcart.hellosearchapi;

import co.elastic.clients.elasticsearch.esql.ElasticsearchEsqlClient;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SearchController {

    private final ElasticsearchClient esClient;

    @GetMapping("/exists/{indexName}/{documentId}")
    public boolean exists(@PathVariable(name = "indexName") String indexName, @PathVariable(name = "documentId") String documentId) throws Exception {
        log.info(">>> 문서 ID 존재 여부 : {}, {}", indexName, documentId);
        return esClient.exists(b -> b.index(indexName)
                .id(documentId))
            .value();
    }

}
