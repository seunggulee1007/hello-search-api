package com.emotionalcart.hellosearchapi.application.product;

import com.emotionalcart.hellosearchapi.domain.elastic.product.ElasticProduct;
import com.emotionalcart.hellosearchapi.domain.elastic.ElasticProductRepository;
import com.emotionalcart.hellosearchapi.domain.entity.Product;
import com.emotionalcart.hellosearchapi.infra.repository.ProductRepository;
import com.emotionalcart.hellosearchapi.presentation.ProductSaveRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ElasticProductRepository elasticProductRepository;

    public List<Product> saveAllProduct() {
        List<Product> all = productRepository.findAll();
        List<ElasticProduct> allElastic = new ArrayList<>();
        int count = 300;
        for (Product product : all) {
            allElastic.add(ElasticProduct.of(product));
            if(allElastic.size() % count == 0) {
                elasticProductRepository.saveAll(allElastic);
                allElastic = new ArrayList<>();
            }
        }
        if(!CollectionUtils.isEmpty(allElastic)) {
            elasticProductRepository.saveAll(allElastic);
        }
        return all;
    }

    public void saveProductToElastic(ProductSaveRequest request) {
        elasticProductRepository.save(request.mapToElasticProduct());
    }

}
