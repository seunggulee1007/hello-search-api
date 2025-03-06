package com.emotionalcart.hellosearchapi.application.order;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.util.ObjectBuilder;
import com.emotionalcart.hellosearchapi.domain.elastic.order.ElasticOrder;
import com.emotionalcart.hellosearchapi.presentation.common.AllSearchCondition;
import com.emotionalcart.hellosearchapi.presentation.product.SortOption;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;

import static com.emotionalcart.hellosearchapi.infra.utils.ElasticSearchUtil.getHighlight;

@Service
@RequiredArgsConstructor
public class OrderSearchService {

    private final ElasticsearchClient esClient;

    public List<OrderSearchResponse> searchByQuery(AllSearchCondition condition, Query query) throws IOException {
        SortOption sortOption = condition.getSortOption();
        SortOrder sortOrder = sortOption.getDirection().equals("ASC") ? SortOrder.Asc : SortOrder.Desc;
        SearchRequest searchRequest = SearchRequest.of(s -> s
            .index("order_index")
            .query(getQuery(query))
            .from(getFrom(condition))
            .size(condition.getSize())
            //.sort(sort -> sort.field(f -> f.field("orderAt").order(sortOrder)))
            .highlight(getHighlight())
        );
        SearchResponse<ElasticOrder> response = esClient.search(searchRequest, ElasticOrder.class);
        return response.hits().hits().stream()
            .map(mapToResponse()).toList();
    }

    private static Function<Hit<ElasticOrder>, OrderSearchResponse> mapToResponse() {
        return hit -> {
            assert hit.source() != null;
            return OrderSearchResponse.of(hit.source(), hit.highlight());
        };
    }

    private static int getFrom(AllSearchCondition condition) {
        return (condition.getPage() - 1) * condition.getSize();
    }

    private static Function<Query.Builder, ObjectBuilder<Query>> getQuery(Query query) {
        return q -> q
            .bool(b -> b
                .filter(query)
            );
    }

    private static ObjectBuilder<SortOptions> getSortOption(SortOptions.Builder sort, SortOption sortOption, SortOrder sortOrder) {
        return sort.field(f -> f.field(sortOption.getField())
            .order(sortOrder));
    }

}
