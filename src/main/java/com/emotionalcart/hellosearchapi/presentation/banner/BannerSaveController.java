package com.emotionalcart.hellosearchapi.presentation.banner;

import com.emotionalcart.hellosearchapi.application.banner.BannerSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/banners")
public class BannerSaveController {

    private final BannerSaveService bannerSaveService;

    public void saveBannersAll() {
        bannerSaveService.saveAll();
    }

}
