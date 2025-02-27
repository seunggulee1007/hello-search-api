package com.emotionalcart.hellosearchapi.application.product;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.SourceConfig;
import com.emotionalcart.hellosearchapi.domain.elastic.product.ElasticProduct;
import com.emotionalcart.hellosearchapi.presentation.product.ProductSearchRequest;
import com.emotionalcart.hellosearchapi.presentation.product.SortOption;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductSearchService {

    private final ElasticsearchClient esClient;

    public List<ElasticProduct> searchProductByAutocomplete(String keyword) throws IOException {
        Query query = Query.of(q -> q.prefix(p -> p.field("name").value(keyword)));
        SourceConfig sourceConfig = SourceConfig.of(sc -> sc.filter(sf -> sf.includes("id", "name", "description")));
        SearchRequest request = SearchRequest.of(s -> s.index("product_index").source(sourceConfig).query(query).size(5));
        SearchResponse<ElasticProduct> response = esClient.search(request, ElasticProduct.class);
        return response.hits().hits().stream().map(Hit::source).toList();
    }

    public List<ElasticProduct> searchSimilarProduct(String productId) throws IOException {
        Query query = Query.of(q -> q
            .moreLikeThis(mlt -> mlt
                .fields("name", "description") // 유사도를 비교할 필드
                .like(l -> l
                    .document(d -> d
                        .index("product_index")
                        .id(productId) // 기준 상품 ID
                    )
                )
                .minTermFreq(1) // 최소 단어 빈도
                .maxQueryTerms(10) // 비교할 최대 단어 수
            )
        );

        SearchRequest request = SearchRequest.of(s -> s.index("product_index").query(query).size(5));
        SearchResponse<ElasticProduct> response = esClient.search(request, ElasticProduct.class);
        return response.hits().hits().stream().map(Hit::source).toList();
    }

    public List<ElasticProduct> searchByQuery(ProductSearchRequest searchRequest) throws IOException {
        Query query = Query.of(q ->
            q.bool(b -> b.must(m -> m.match(t ->
                                               t.field("combinedField")
                                                   .query(searchRequest.getKeyword())

                                   )
                   ).filter(f -> f.range(RangeQuery.of(r -> r.number(v -> v.field("price").gte((double)searchRequest.getMinPrice()).lte((double)searchRequest.getMaxPrice())))))
            )
        );
        SortOption sortOption = searchRequest.getSortOption();
        SortOrder sortOrder = sortOption.getDirection().equals("ASC") ? SortOrder.Asc : SortOrder.Desc;
        SearchRequest request =SearchRequest.of(s -> s.index("product_index")
            .query(query)
            .from((searchRequest.getPage()-1) * searchRequest.getSize())
            .size(searchRequest.getSize())

            .sort(sort -> sort.field(f -> f.field(sortOption.getField())
                .order(sortOrder))));


        SearchResponse<ElasticProduct> response = esClient.search(request, ElasticProduct.class);
        return response.hits().hits().stream().map(Hit::source).toList();
    }
}
