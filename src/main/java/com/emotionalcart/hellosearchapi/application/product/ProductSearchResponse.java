package com.emotionalcart.hellosearchapi.application.product;

import com.emotionalcart.hellosearchapi.domain.elastic.product.ElasticProduct;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ProductSearchResponse {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String name;

    private String description;

    private Integer price;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long providerId;

    private String providerName;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long categoryId;

    private String categoryName;

    private String combinedField;

    private Integer salesCount;

    private List<ProductOptionResponse> options;

    private LocalDateTime createdAt;

    public static ProductSearchResponse of(ElasticProduct result) {
        ProductSearchResponse response = new ProductSearchResponse();
        response.id = result.getId();
        response.name = result.getName();
        response.description = result.getDescription();
        response.price = result.getPrice();
        response.providerId = result.getProviderId();
        response.providerName = result.getProviderName();
        response.categoryId = result.getCategoryId();
        response.categoryName = result.getCategoryName();
        response.combinedField = result.getCombinedField();
        response.salesCount = result.getSalesCount();
        response.createdAt = result.getCreatedAt();
        response.options = result.getOptions().stream()
            .map(option -> {
                ProductOptionResponse optionResponse = new ProductOptionResponse();
                optionResponse.id = option.getId();
                optionResponse.optionName = option.getOptionName();
                optionResponse.details = option.getDetails().stream()
                    .map(detail -> {
                        ProductOptionResponse.ProductOptionDetailResponse detailResponse =
                            new ProductOptionResponse.ProductOptionDetailResponse();
                        detailResponse.id = detail.getId();
                        detailResponse.optionDetailName = detail.getOptionDetailName();
                        detailResponse.additionalPrice = detail.getAdditionalPrice();
                        return detailResponse;
                    })
                    .toList();
                return optionResponse;
            })
            .toList();

        return response;
    }

    @Getter
    private static class ProductOptionResponse {

        @JsonSerialize(using = ToStringSerializer.class)
        private Long id;
        private String optionName;
        private List<ProductOptionDetailResponse> details;

        @Getter
        private static class ProductOptionDetailResponse {

            @JsonSerialize(using = ToStringSerializer.class)
            private Long id;
            private String optionDetailName;
            private Integer additionalPrice;

        }

    }

}
