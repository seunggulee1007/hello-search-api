package com.emotionalcart.hellosearchapi.presentation.banner;

import com.emotionalcart.hellosearchapi.application.banner.BannerSaveRequest;
import com.emotionalcart.hellosearchapi.application.banner.BannerSaveService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/banners")
public class BannerSaveController {

    private final BannerSaveService bannerSaveService;

    @Hidden
    @PostMapping("/all")
    public void saveBannersAll() {
        bannerSaveService.saveAll();
    }

    @PostMapping
    @Operation(summary = "배너 저장", description = "배너를 저장합니다.")
    public void saveBanner(BannerSaveRequest request) {
        bannerSaveService.save(request);
    }

}
