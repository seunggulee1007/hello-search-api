package com.emotionalcart.hellosearchapi.presentation.product;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.SourceConfig;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import co.elastic.clients.elasticsearch.core.search.TotalHitsRelation;
import com.emotionalcart.hellosearchapi.application.product.ProductSearchResponse;
import com.emotionalcart.hellosearchapi.application.product.ProductSearchService;
import com.emotionalcart.hellosearchapi.domain.elastic.product.ElasticProduct;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/elastic/product")
public class ProductSearchController {

    private final ElasticsearchClient esClient;
    private final ProductSearchService productSearchService;

    @Hidden
    @GetMapping("/exists/{indexName}/{documentId}")
    public boolean exists(@PathVariable(name = "indexName") String indexName, @PathVariable(name = "documentId") String documentId) throws
        Exception {
        log.info(">>> 문서 ID 존재 여부 : {}, {}", indexName, documentId);
        return esClient.exists(b -> b.index(indexName)
                .id(documentId))
            .value();
    }

    @Hidden
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
        if (isExactResult) {
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

    @Hidden
    @GetMapping("/search/keyword/{indexName}/{fieldName}/{query}")
    public List<String> searchKeyword(@PathVariable(name = "indexName") String indexName,
                                      @PathVariable(name = "fieldName") String fieldName,
                                      @PathVariable(name = "query") String queryText) throws Exception {
        log.info(">>> 검색 : {}, {}", indexName, queryText);
        String[] queries = queryText.split(",");
        List<Query> queryList =
            Arrays.stream(queries).map(text -> Query.of(q -> q.matchPhrase(m -> m.field(fieldName).query(text)))).toList();
        SourceConfig sourceConfig = SourceConfig.of(sc -> sc.filter(sf -> sf.includes("name", "age")));
        Query query = Query.of(q -> q.bool(b -> b.should(queryList)));
        SearchResponse<Object> search = esClient.search(s -> s
                                                            .index(indexName)
                                                            .source(sourceConfig)
                                                            .query(query)
            , Object.class
        );
        TotalHits totalHits = search.hits().total();
        boolean isExactResult = totalHits.relation() == TotalHitsRelation.Eq;
        if (isExactResult) {
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

    @Operation(summary = "추천어 검색", description = "입력한 키워드의 유사도를 파악해 5개의 추천어를 준다.")
    @GetMapping("/autocomplete")
    public ResponseEntity<List<ElasticProduct>> autocomplete(@RequestParam(value = "keyword", required = false) String keyword,
                                                             @RequestParam(value = "requestCount") int requestCount) throws IOException {
        return ResponseEntity.ok(productSearchService.searchProductByAutocomplete(keyword, requestCount));
    }

    @Operation(summary = "유사 상품 검색", description = "검색한 상품의 유사도를 파악해 n개의 유사 상품을 준다.")
    @GetMapping("/similar/{productId}")
    public ResponseEntity<List<ElasticProduct>> similar(@PathVariable String productId,
                                                        @RequestParam(value = "requestCount") int requestCount) throws IOException {
        return ResponseEntity.ok(productSearchService.searchSimilarProduct(productId, requestCount));
    }

    @Operation(summary = "상품 검색", description = "상품을 검색한다.")
    @GetMapping
    public ResponseEntity<List<ProductSearchResponse>> search(ProductSearchRequest searchRequest) throws IOException {
        return ResponseEntity.ok(productSearchService.searchByQuery(searchRequest));
    }

}
