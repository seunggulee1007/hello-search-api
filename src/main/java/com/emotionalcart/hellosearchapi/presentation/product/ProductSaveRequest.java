package com.emotionalcart.hellosearchapi.presentation.product;

import com.emotionalcart.hellosearchapi.domain.elastic.product.ElasticProduct;
import com.emotionalcart.hellosearchapi.domain.elastic.product.ElasticProductOption;
import com.emotionalcart.hellosearchapi.domain.elastic.product.ElasticProductOptionDetail;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class ProductSaveRequest {

    private Long id;

    private String name;

    private String description;

    private Integer price;

    private Long providerId;

    private String providerName;

    private Long categoryId;

    private String categoryName;

    @Builder.Default
    private List<ProductOptionRequest> options = new ArrayList<>();

    public ElasticProduct mapToElasticProduct() {
        return ElasticProduct.builder()
                .id(id)
                .name(name)
                .description(description)
                .price(price)
                .providerId(providerId)
                .providerName(providerName)
                .categoryId(categoryId)
                .categoryName(categoryName)
                .options(
                    CollectionUtils.isEmpty(options) ? new ArrayList<>() : options.stream().map(ProductOptionRequest::mapToElasticProductOption).toList())
                .build();
    }

    @Getter
    @Builder
    public static class ProductOptionRequest {

        private Long id;
        private String optionName;
        private List<ProductOptionDetailRequest> details;

        public ElasticProductOption mapToElasticProductOption() {
            return ElasticProductOption.builder()
                    .id(id)
                    .optionName(optionName)
                    .details(
                        CollectionUtils.isEmpty(details) ? new ArrayList<>() : details.stream().map(ProductOptionDetailRequest::mapToElasticProductOptionDetail).toList())
                    .build();
        }

    }

    @Getter
    @Builder
    public static class ProductOptionDetailRequest {
        private Long id;
        private String detailName;
        private Integer additionalPrice;

        public ElasticProductOptionDetail mapToElasticProductOptionDetail() {
            return ElasticProductOptionDetail.builder()
                    .id(id)
                    .optionDetailName(detailName)
                    .additionalPrice(additionalPrice)
                    .build();
        }

    }

}
