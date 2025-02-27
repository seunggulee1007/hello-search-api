package com.emotionalcart.hellosearchapi.application.product;

import com.emotionalcart.hellosearchapi.infra.elasticrepository.ElasticProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductSaveService {

    private final ElasticProductRepository elasticProductRepository;

}
