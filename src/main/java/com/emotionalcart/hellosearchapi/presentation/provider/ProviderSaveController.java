package com.emotionalcart.hellosearchapi.presentation.provider;

import com.emotionalcart.hellosearchapi.application.provider.ProviderSaveRequest;
import com.emotionalcart.hellosearchapi.application.provider.ProviderSaveService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/providers")
public class ProviderSaveController {

    private final ProviderSaveService providerSaveService;

    @PostMapping
    @Operation(summary = "제휴사 저장", description = "제휴사를 저장합니다.")
    public void saveProvider(ProviderSaveRequest request) {
        providerSaveService.save(request);
    }

    @Hidden
    @PostMapping("/all")
    public void saveAll() {
        providerSaveService.saveAll();
    }

}
