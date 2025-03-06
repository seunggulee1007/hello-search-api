package com.emotionalcart.hellosearchapi.application.provider;

import com.emotionalcart.hellosearchapi.infra.elasticrepository.ElasticProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProviderDeleteService {

    private final ElasticProviderRepository elasticProviderRepository;

    public void deleteProvider(Long providerId) {
        elasticProviderRepository.deleteById(providerId);
    }

}
