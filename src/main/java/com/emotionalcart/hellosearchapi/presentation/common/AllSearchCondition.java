package com.emotionalcart.hellosearchapi.presentation.common;

import com.emotionalcart.hellosearchapi.presentation.product.SortOption;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AllSearchCondition {

    protected String keyword;

    protected int page = 1;

    protected int size = 10;

    protected SortOption sortOption = SortOption.CREATE_DESC;

}
