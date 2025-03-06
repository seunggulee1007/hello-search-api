package com.emotionalcart.hellosearchapi.application.admin;

import com.emotionalcart.hellosearchapi.application.order.OrderSearchResponse;
import com.emotionalcart.hellosearchapi.presentation.product.ProductAdminResponse;
import lombok.Getter;

import java.util.List;

@Getter
public class AdminSearchResponse {

    List<ProductAdminResponse> products;

    List<OrderSearchResponse> orders;

    public static AdminSearchResponse of(List<ProductAdminResponse> products, List<OrderSearchResponse> orders) {
        AdminSearchResponse response = new AdminSearchResponse();
        response.products = products;
        response.orders = orders;
        return response;
    }

}
