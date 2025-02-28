package com.emotionalcart.hellosearchapi.application.product;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.UpdateRequest;
import com.emotionalcart.hellosearchapi.domain.elastic.product.ElasticProduct;
import com.emotionalcart.hellosearchapi.infra.elasticrepository.ElasticProductRepository;
import com.emotionalcart.hellosearchapi.domain.entity.Product;
import com.emotionalcart.hellosearchapi.infra.repository.ProductRepository;
import com.emotionalcart.hellosearchapi.presentation.product.ProductSaveRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ElasticProductRepository elasticProductRepository;
    private final ElasticsearchClient esClient;

    public void saveAllProduct() {
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
    }

    public void saveProductToElastic(ProductSaveRequest request) {
        elasticProductRepository.save(request.mapToElasticProduct());
    }

    public void upsertProductToElastic(ProductSaveRequest request) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL); // NULL 값 제외

        ElasticProduct elasticProduct = request.mapToElasticProduct();
        UpdateRequest<ElasticProduct, ElasticProduct> updateRequest = UpdateRequest.of(u -> u
            .index("product_index")
            .id(String.valueOf(elasticProduct.getId()))
            .doc(elasticProduct)
            .upsert(elasticProduct)
            .docAsUpsert(true)
        );
        esClient.update(updateRequest, ElasticProduct.class);

    }

}
