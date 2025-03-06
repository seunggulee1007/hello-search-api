package com.emotionalcart.hellosearchapi.application.provider;

import com.emotionalcart.hellosearchapi.domain.elastic.provider.ElasticProvider;
import com.emotionalcart.hellosearchapi.domain.entity.Provider;
import com.emotionalcart.hellosearchapi.infra.elasticrepository.ElasticProviderRepository;
import com.emotionalcart.hellosearchapi.infra.repository.ProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProviderSaveService {

    private final ElasticProviderRepository elasticProviderRepository;
    private final ProviderRepository providerRepository;

    public void saveAll() {
        List<Provider> providers = providerRepository.findAll();
        List<ElasticProvider> providerList = providers.stream().map(Provider::mapToElastic).toList();
        elasticProviderRepository.saveAll(providerList);
    }

    /**
     * <pre>
     * 공급자 저장
     * </pre>
     *
     * @param request 공급자 정보
     */
    public void save(ProviderSaveRequest request) {
        elasticProviderRepository.save(request.mapToElasticProvider());
    }

    /**
     * <pre>
     * 공급자 삭제
     * </pre>
     *
     * @param id 공급자 ID
     */
    public void delete(Long id) {
        elasticProviderRepository.deleteById(id);
    }

}
