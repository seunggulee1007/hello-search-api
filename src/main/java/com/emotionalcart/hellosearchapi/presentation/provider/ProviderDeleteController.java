package com.emotionalcart.hellosearchapi.presentation.provider;

import com.emotionalcart.hellosearchapi.application.provider.ProviderDeleteService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/providers")
public class ProviderDeleteController {

    private final ProviderDeleteService providerDeleteService;

    @Operation(summary = "판매처 삭제", description = "판매처를 삭제합니다.")
    @DeleteMapping("/{providerId}")
    public void deleteProvider(@PathVariable("providerId") Long providerId) throws IOException {
        providerDeleteService.deleteProvider(providerId);
    }

}
