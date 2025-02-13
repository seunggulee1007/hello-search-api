package com.emotionalcart.hellosearchapi;

import lombok.Getter;

import java.util.List;

@Getter
public class ProductOption {

    private String optionName;

    private List<ProductOptionDetail> details;
}
