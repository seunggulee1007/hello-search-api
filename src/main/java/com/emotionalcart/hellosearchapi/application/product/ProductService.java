package com.emotionalcart.hellosearchapi.application.product;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.UpdateRequest;
import co.elastic.clients.elasticsearch.core.UpdateResponse;
import co.elastic.clients.json.JsonData;
import com.emotionalcart.hellosearchapi.domain.elastic.order.ElasticSalesCount;
import com.emotionalcart.hellosearchapi.domain.elastic.product.ElasticProduct;
import com.emotionalcart.hellosearchapi.domain.entity.Product;
import com.emotionalcart.hellosearchapi.infra.elasticrepository.ElasticProductRepository;
import com.emotionalcart.hellosearchapi.infra.repository.ProductRepository;
import com.emotionalcart.hellosearchapi.presentation.product.ProductSaveRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            if (allElastic.size() % count == 0) {
                elasticProductRepository.saveAll(allElastic);
                allElastic = new ArrayList<>();
            }
        }
        if (!CollectionUtils.isEmpty(allElastic)) {
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

    public void syncProductSales(List<ElasticSalesCount> salesCounts) throws IOException {
        // Painless 스크립트를 이용하여 기존 sales 값에 sold_count 추가
        String scriptSource = "ctx._source.salesCount = (ctx._source.salesCount != null ? ctx._source.salesCount : 0) + params.sold_count";

        for (ElasticSalesCount salesCount : salesCounts) {
            Map<String, JsonData> params = new HashMap<>();
            params.put("sold_count", JsonData.of(salesCount.getCount()));

            // Elasticsearch 업데이트 요청 생성
            UpdateRequest<Object, Object> request = UpdateRequest.of(u -> u
                .index("product_index")  // 인덱스 이름
                .id(salesCount.getId())  // 업데이트할 문서 ID
                .script(s -> s
                    .source(scriptSource)
                    .params(params)
                )
            );
            // 업데이트 실행
            UpdateResponse<Object> response = esClient.update(request, Object.class);
            System.out.println("Update result: " + response.result());
        }
    }

}
