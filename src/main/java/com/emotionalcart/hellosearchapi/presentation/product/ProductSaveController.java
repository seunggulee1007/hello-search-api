package com.emotionalcart.hellosearchapi.presentation.product;

import com.emotionalcart.hellosearchapi.infra.elasticrepository.ElasticProductRepository;
import com.emotionalcart.hellosearchapi.application.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ProductSaveController {

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
