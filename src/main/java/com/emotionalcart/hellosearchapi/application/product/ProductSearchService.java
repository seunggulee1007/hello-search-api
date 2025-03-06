package com.emotionalcart.hellosearchapi.application.product;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.SourceConfig;
import co.elastic.clients.util.ObjectBuilder;
import com.emotionalcart.hellosearchapi.domain.elastic.product.ElasticProduct;
import com.emotionalcart.hellosearchapi.presentation.common.AllSearchCondition;
import com.emotionalcart.hellosearchapi.presentation.product.ProductAdminResponse;
import com.emotionalcart.hellosearchapi.presentation.product.ProductSearchRequest;
import com.emotionalcart.hellosearchapi.presentation.product.SortOption;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;

import static com.emotionalcart.hellosearchapi.infra.utils.ElasticSearchUtil.getHighlight;

@Service
@RequiredArgsConstructor
public class ProductSearchService {

    private final ElasticsearchClient esClient;

    public List<ElasticProduct> searchProductByAutocomplete(String keyword, int requestCount) throws IOException {
        Query query = Query.of(q -> q.prefix(p -> p.field("name").value(keyword)));
        SourceConfig sourceConfig = SourceConfig.of(sc -> sc.filter(sf -> sf.includes("id", "name", "description")));
        int finalRequestCount = getFinalRequestCount(requestCount);
        SearchRequest request = SearchRequest.of(s -> s.index("product_index").source(sourceConfig).query(query).size(finalRequestCount));
        SearchResponse<ElasticProduct> response = esClient.search(request, ElasticProduct.class);
        return response.hits().hits().stream().map(Hit::source).toList();
    }

    public List<ElasticProduct> searchSimilarProduct(String productId, int requestCount) throws IOException {
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

        int finalRequestCount = getFinalRequestCount(requestCount);

        SearchRequest request = SearchRequest.of(s -> s.index("product_index").query(query).size(finalRequestCount));
        SearchResponse<ElasticProduct> response = esClient.search(request, ElasticProduct.class);
        return response.hits().hits().stream().map(Hit::source).toList();
    }

    private static int getFinalRequestCount(int requestCount) {
        return requestCount == 0 ? 5 : requestCount;
    }

    public List<ProductSearchResponse> searchByQuery(ProductSearchRequest searchRequest) throws IOException {
        Query query = getSearchQuery(searchRequest);
        SearchRequest request = getSearchRequest(searchRequest, query);

        SearchResponse<ElasticProduct> response = esClient.search(request, ElasticProduct.class);

        return response.hits().hits().stream().map(h -> {
            assert h.source() != null;
            return ProductSearchResponse.of(h.source());
        }).toList();
    }

    private static Query getSearchQuery(ProductSearchRequest searchRequest) {
        return Query.of(q ->
                            q.bool(b -> b.must(m -> m.match(t ->
                                                                t.field("combinedField")
                                                                    .query(searchRequest.getKeyword())
                                               )
                                       ).filter(categoryIdFilter(searchRequest))
                                       .filter(minMaxPriceFilter(searchRequest))
                            )
        );
    }

    private static SearchRequest getSearchRequest(AllSearchCondition searchRequest, Query query) {
        SortOption sortOption = searchRequest.getSortOption();
        SortOrder sortOrder = sortOption.getDirection().equals("ASC") ? SortOrder.Asc : SortOrder.Desc;
        return SearchRequest.of(s -> s.index("product_index")
            .query(query)
            .from((searchRequest.getPage() - 1) * searchRequest.getSize())
            .size(searchRequest.getSize())
            .sort(sort -> sort.field(f -> f.field(sortOption.getField())
                .order(sortOrder))));
    }

    public List<ProductAdminResponse> searchAdminByQuery(AllSearchCondition searchRequest, Query query) throws IOException {
        SortOption sortOption = searchRequest.getSortOption();
        SortOrder sortOrder = sortOption.getDirection().equals("ASC") ? SortOrder.Asc : SortOrder.Desc;
        SearchRequest request = SearchRequest.of(s -> s.index("product_index")
            .query(query)
            .from((searchRequest.getPage() - 1) * searchRequest.getSize())
            .size(searchRequest.getSize())
            .sort(sort -> sort.field(f -> f.field(sortOption.getField())
                .order(sortOrder))).highlight(getHighlight()));

        SearchResponse<ElasticProduct> response = esClient.search(request, ElasticProduct.class);

        return response.hits().hits().stream().map(h -> {
            assert h.source() != null;
            return ProductAdminResponse.of(h.source(), h.highlight());
        }).toList();
    }

    private static Function<Query.Builder, ObjectBuilder<Query>> minMaxPriceFilter(ProductSearchRequest searchRequest) {
        return f -> f.range(RangeQuery.of(r -> r.number(v -> v.field("price").gte((double)searchRequest.getMinPrice()).lte(
            (double)searchRequest.getMaxPrice()))));
    }

    private static Function<Query.Builder, ObjectBuilder<Query>> categoryIdFilter(ProductSearchRequest searchRequest) {
        if (searchRequest.getCategoryId() != null) {
            return f -> f.term(t -> t.field("categoryId").value(searchRequest.getCategoryId()));
        }
        return f -> f.matchAll(m -> m);
    }

}
