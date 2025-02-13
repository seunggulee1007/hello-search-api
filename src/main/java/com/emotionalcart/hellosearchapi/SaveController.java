package com.emotionalcart.hellosearchapi;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SaveController {

    private final ProductRepository productRepository;

    @PostMapping
    public void save(@RequestBody Product product) {
        productRepository.save(product);
    }
}
