package com.emotionalcart.hellosearchapi.infra.utils;

import co.elastic.clients.elasticsearch.core.search.Highlight;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ElasticSearchUtil {

    public static Highlight getHighlight() {
        return Highlight.of(h -> h
            .fields("combinedField", f -> f
                .fragmentSize(20)
                .numberOfFragments(1)
                .preTags("<span style=\"color:blue;font-weight:bold;\">")
                .postTags("</span>")
            )
        );
    }

}
