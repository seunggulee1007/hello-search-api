package com.emotionalcart.hellosearchapi;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.SourceConfig;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import co.elastic.clients.elasticsearch.core.search.TotalHitsRelation;
import com.emotionalcart.hellosearchapi.elastic.ElasticProduct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SearchController {

    private final ElasticsearchClient esClient;

    @GetMapping("/exists/{indexName}/{documentId}")
    public boolean exists(@PathVariable(name = "indexName") String indexName, @PathVariable(name = "documentId") String documentId) throws
        Exception {
        log.info(">>> 문서 ID 존재 여부 : {}, {}", indexName, documentId);
        return esClient.exists(b -> b.index(indexName)
                .id(documentId))
            .value();
    }

    @GetMapping("/search/{indexName}/{fieldName}/{query}")
    public List<String> search(@PathVariable(name = "indexName") String indexName,
                               @PathVariable(name = "fieldName") String fieldName,
                               @PathVariable(name = "query") String query) throws Exception {
        log.info(">>> 검색 : {}, {}", indexName, query);
        SourceConfig sourceConfig = SourceConfig.of(sc -> sc.filter(sf -> sf.includes("name", "age")));
        SearchResponse<Object> search = esClient.search(s -> s
                                                            .index(indexName)
                                                            .source(sourceConfig)
                                                            .query(q -> q.matchPhrase(m -> m
                                                                    .field(fieldName)
                                                                    .query(query)
                                                                )
                                                            ),
                                                        Object.class
        );
        TotalHits totalHits = search.hits().total();
        boolean isExactResult = totalHits.relation() == TotalHitsRelation.Eq;
        if(isExactResult) {
            log.info(">>> 정확한 결과 : {}", totalHits.value());
        } else {
            log.info(">>> 대략적인 결과 : {}", totalHits.value());
        }
        return search.hits().hits().stream()
            .map(Hit::source)
            .filter(Objects::nonNull)
            .map(Object::toString)
            .toList();
    }

    @GetMapping("/search/keyword/{indexName}/{fieldName}/{query}")
    public List<String> searchKeyword(@PathVariable(name = "indexName") String indexName,
                               @PathVariable(name = "fieldName") String fieldName,
                               @PathVariable(name = "query") String queryText) throws Exception {
        log.info(">>> 검색 : {}, {}", indexName, queryText);
        String[] queries = queryText.split(",");
        List<Query> queryList = Arrays.stream(queries).map(text -> Query.of(q -> q.matchPhrase(m -> m.field(fieldName).query(text)))).toList();
        SourceConfig sourceConfig = SourceConfig.of(sc -> sc.filter(sf -> sf.includes("name", "age")));
        Query query = Query.of(q -> q.bool(b -> b.should(queryList)));
        SearchResponse<Object> search = esClient.search(s -> s
                                                            .index(indexName)
                                                            .source(sourceConfig)
                                                            .query(query)
                                                            ,Object.class
        );
        TotalHits totalHits = search.hits().total();
        boolean isExactResult = totalHits.relation() == TotalHitsRelation.Eq;
        if(isExactResult) {
            log.info(">>> 정확한 결과 : {}", totalHits.value());
        } else {
            log.info(">>> 대략적인 결과 : {}", totalHits.value());
        }
        return search.hits().hits().stream()
            .map(Hit::source)
            .filter(Objects::nonNull)
            .map(Object::toString)
            .toList();
    }

    @GetMapping("/autocomplete/{keyword}")
    public List<ElasticProduct> autocomplete(@PathVariable String keyword) throws IOException {
        Query query = Query.of(q -> q.prefix(p -> p.field("name").value(keyword)));
        SourceConfig sourceConfig = SourceConfig.of(sc -> sc.filter(sf -> sf.includes("id","name", "description")));
        SearchRequest request = SearchRequest.of(s -> s.index("my_index").source(sourceConfig).query(query).size(5));
        SearchResponse<ElasticProduct> response = esClient.search(request, ElasticProduct.class);
        return response.hits().hits().stream().map(Hit::source).toList();
    }

    @GetMapping("/similar/{productId}")
    public List<ElasticProduct> similar(@PathVariable String productId) throws IOException {
        Query query = Query.of(q -> q
            .moreLikeThis(mlt -> mlt
                .fields("name", "description", "tags") // 유사도를 비교할 필드
                .like(l -> l
                    .document(d -> d
                        .index("products")
                        .id(productId) // 기준 상품 ID
                    )
                )
                .minTermFreq(1) // 최소 단어 빈도
                .maxQueryTerms(10) // 비교할 최대 단어 수
            )
        );

        SearchRequest request = SearchRequest.of(s -> s.index("products").query(query).size(5));
        SearchResponse<ElasticProduct> response = esClient.search(request, ElasticProduct.class);
        return response.hits().hits().stream().map(Hit::source).toList();
    }

}
