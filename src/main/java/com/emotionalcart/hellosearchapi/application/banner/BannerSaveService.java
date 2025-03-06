package com.emotionalcart.hellosearchapi.application.banner;

import com.emotionalcart.hellosearchapi.domain.elastic.banner.ElasticBanner;
import com.emotionalcart.hellosearchapi.domain.entity.Banner;
import com.emotionalcart.hellosearchapi.infra.elasticrepository.ElasticBannerRepository;
import com.emotionalcart.hellosearchapi.infra.repository.BannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BannerSaveService {

    private final ElasticBannerRepository elasticBannerRepository;
    private final BannerRepository bannerRepository;

    public void save(BannerSaveRequest request) {
        elasticBannerRepository.save(request.mapToElasticBanner());
    }

    public void saveAll() {
        List<Banner> bannerList = bannerRepository.findAll();
        List<ElasticBanner> banners = bannerList.stream().map(Banner::mapToElasticBanner).toList();
        elasticBannerRepository.saveAll(banners);
    }

}
