package com.emotionalcart.hellosearchapi.service;

import com.emotionalcart.hellosearchapi.elastic.ElasticProduct;
import com.emotionalcart.hellosearchapi.elastic.ElasticProductRepository;
import com.emotionalcart.hellosearchapi.entity.Product;
import com.emotionalcart.hellosearchapi.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ElasticProductRepository elasticProductRepository;

    public List<Product> saveAllProduct() {
        List<Product> all = productRepository.findAll();
        int count = 0;
        for (Product product : all) {
            if (count++ == 10) {
                break;
            }
            elasticProductRepository.save(ElasticProduct.of(product));
        }
        return all;
    }

}
