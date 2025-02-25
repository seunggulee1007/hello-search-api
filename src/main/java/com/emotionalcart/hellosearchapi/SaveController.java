package com.emotionalcart.hellosearchapi;

import com.emotionalcart.hellosearchapi.elastic.ElasticProduct;
import com.emotionalcart.hellosearchapi.elastic.ElasticProductRepository;
import com.emotionalcart.hellosearchapi.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
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
    public void save(@RequestBody ElasticProduct elasticProduct) {
        elasticProductRepository.save(elasticProduct);
    }

    @PostMapping("/all")
    public void saveAll() {
        productService.saveAllProduct();
    }

}
