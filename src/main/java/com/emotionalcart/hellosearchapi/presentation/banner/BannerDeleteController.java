package com.emotionalcart.hellosearchapi.presentation.banner;

import com.emotionalcart.hellosearchapi.application.banner.BannerDeleteService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/banners")
public class BannerDeleteController {

    private final BannerDeleteService bannerDeleteService;

    @Operation(summary = "배너 삭제", description = "배너를 삭제합니다.")
    @DeleteMapping("/{bannerId}")
    public void deleteBanner(@PathVariable("bannerId") Long bannerId) {
        bannerDeleteService.deleteBanner(bannerId);
    }

}
