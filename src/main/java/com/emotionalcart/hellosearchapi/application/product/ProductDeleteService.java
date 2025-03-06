package com.emotionalcart.hellosearchapi.application.product;

import com.emotionalcart.hellosearchapi.infra.elasticrepository.ElasticProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductDeleteService {

    private final ElasticProductRepository elasticProductRepository;

    public void deleteProduct(Long productId) {
        elasticProductRepository.deleteById(productId);
    }

}
