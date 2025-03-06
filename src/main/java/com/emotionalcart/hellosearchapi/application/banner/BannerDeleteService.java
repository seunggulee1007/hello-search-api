package com.emotionalcart.hellosearchapi.application.banner;

import com.emotionalcart.hellosearchapi.infra.elasticrepository.ElasticBannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BannerDeleteService {

    private final ElasticBannerRepository elasticBannerRepository;

    public void deleteBanner(Long bannerId) {
        elasticBannerRepository.deleteById(bannerId);
    }

}
