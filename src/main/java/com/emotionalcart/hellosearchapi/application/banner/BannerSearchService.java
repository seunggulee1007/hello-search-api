package com.emotionalcart.hellosearchapi.application.banner;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.emotionalcart.hellosearchapi.domain.elastic.banner.ElasticBanner;
import com.emotionalcart.hellosearchapi.presentation.common.AllSearchCondition;
import com.emotionalcart.hellosearchapi.presentation.product.SortOption;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static com.emotionalcart.hellosearchapi.infra.utils.ElasticSearchUtil.getHighlight;

@Service
@RequiredArgsConstructor
public class BannerSearchService {

    private final ElasticsearchClient esClient;

    public List<BannerSearchResponse> searchBanners(AllSearchCondition searchRequest, Query query) throws IOException {
        SortOption sortOption = searchRequest.getSortOption();
        SortOrder sortOrder = sortOption.getDirection().equals("ASC") ? SortOrder.Asc : SortOrder.Desc;
        SearchRequest request = SearchRequest.of(s -> s.index("banner_index")
            .query(query)
            .from((searchRequest.getPage() - 1) * searchRequest.getSize())
            .size(searchRequest.getSize())
            .sort(sort -> sort.field(f -> f.field(sortOption.getField())
                .order(sortOrder))).highlight(getHighlight()));
        SearchResponse<ElasticBanner> response = esClient.search(request, ElasticBanner.class);
        return response.hits().hits().stream().map(h -> {
            assert h.source() != null;
            return BannerSearchResponse.of(h.source(), h.highlight());
        }).toList();
    }

}
