package com.emotionalcart.hellosearchapi.presentation.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSearchRequest {

    private String keyword;

    private int page;

    private int size;

    private SortOption sortOption;

    private int minPrice;

    private int maxPrice = Integer.MAX_VALUE;

}
