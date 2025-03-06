package com.emotionalcart.hellosearchapi.presentation.product;

import com.emotionalcart.hellosearchapi.presentation.common.AllSearchCondition;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSearchRequest extends AllSearchCondition {

    private Long categoryId;

    private int minPrice;

    private int maxPrice = Integer.MAX_VALUE;

}
