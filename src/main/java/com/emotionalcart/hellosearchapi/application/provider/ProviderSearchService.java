package com.emotionalcart.hellosearchapi.application.provider;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.emotionalcart.hellosearchapi.domain.elastic.provider.ElasticProvider;
import com.emotionalcart.hellosearchapi.presentation.common.AllSearchCondition;
import com.emotionalcart.hellosearchapi.presentation.product.SortOption;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static com.emotionalcart.hellosearchapi.infra.utils.ElasticSearchUtil.getHighlight;

@Service
@RequiredArgsConstructor
public class ProviderSearchService {

    private final ElasticsearchClient esClient;

    public List<ProviderSearchResponse> searchByQuery(AllSearchCondition condition, Query query) throws IOException {
        SortOption sortOption = condition.getSortOption();
        SortOrder sortOrder = sortOption.getDirection().equals("ASC") ? SortOrder.Asc : SortOrder.Desc;
        SearchRequest searchRequest = SearchRequest.of(s -> s
            .index("provider_index")
            .query(query)
            .from(getFrom(condition))
            .size(condition.getSize())
            .sort(sort -> sort.field(f -> f.field("createdAt").order(sortOrder)))
            .highlight(getHighlight())
        );
        SearchResponse<ElasticProvider> response = esClient.search(searchRequest, ElasticProvider.class);
        return response.hits().hits().stream()
            .map(m -> ProviderSearchResponse.of(m.source(), m.highlight())).toList();
    }

    private static int getFrom(AllSearchCondition condition) {
        return (condition.getPage() - 1) * condition.getSize();
    }

}
