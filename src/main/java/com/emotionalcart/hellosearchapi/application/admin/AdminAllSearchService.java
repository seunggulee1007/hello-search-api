package com.emotionalcart.hellosearchapi.application.admin;

import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Operator;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.emotionalcart.hellosearchapi.application.banner.BannerSearchResponse;
import com.emotionalcart.hellosearchapi.application.banner.BannerSearchService;
import com.emotionalcart.hellosearchapi.application.order.OrderSearchResponse;
import com.emotionalcart.hellosearchapi.application.order.OrderSearchService;
import com.emotionalcart.hellosearchapi.application.product.ProductSearchService;
import com.emotionalcart.hellosearchapi.presentation.common.AllSearchCondition;
import com.emotionalcart.hellosearchapi.presentation.product.ProductAdminResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminAllSearchService {

    private final ProductSearchService productSearchService;
    private final OrderSearchService orderSearchService;
    private final BannerSearchService bannerSearchService;

    public AdminSearchResponse searchAll(AllSearchCondition condition) throws IOException {
        Query query = byFullTextOr(condition.getKeyword());
        List<ProductAdminResponse> products = productSearchService.searchAdminByQuery(condition, query);
        List<OrderSearchResponse> orders = orderSearchService.searchByQuery(condition, query);
        List<BannerSearchResponse> banners = bannerSearchService.searchBanners(condition, query);
        return AdminSearchResponse.of(products, orders, banners);
    }

    private static Query byFullTextOr(String orMatchTexts) {
        if (!StringUtils.hasText(orMatchTexts))
            return null;
        return MatchQuery.of(mq -> mq
                .field("combinedField")
                .query(orMatchTexts.replace(",", " "))
                .operator(Operator.Or))
            ._toQuery();
    }

}
