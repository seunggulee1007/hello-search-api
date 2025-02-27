package com.emotionalcart.hellosearchapi.presentation;

import com.emotionalcart.hellosearchapi.domain.elastic.product.ElasticProduct;
import com.emotionalcart.hellosearchapi.domain.elastic.ElasticProductRepository;
import com.emotionalcart.hellosearchapi.application.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SaveController {

    private final ElasticProductRepository elasticProductRepository;
    private final ProductService productService;

    @PostMapping
    public void save(@RequestBody ProductSaveRequest request) {
        productService.saveProductToElastic(request);
    }

    @PostMapping("/all")
    public void saveAll() {
        productService.saveAllProduct();
    }

}
