package com.emotionalcart.hellosearchapi.presentation.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SortOption {

    PRICE_ASC("price", "ASC"),
    PRICE_DESC("price", "DESC"),
    SALES_DESC("sales", "DESC"),
    CREATE_DESC("createdAt","DESC"),
    CREATE_ASC("createdAt","ASC");

    private final String field;
    private final String direction;

}
