package com.emotionalcart.hellosearchapi.presentation.product;

import com.emotionalcart.hellosearchapi.application.product.ProductService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/elastic/product")
public class ProductSaveController {

    private final ProductService productService;

    @PostMapping
    @Operation(summary = "상품 저장 및 업데이트")
    public void save(@RequestBody ProductSaveRequest request) throws IOException {
        productService.upsertProductToElastic(request);
    }

    @Hidden
    @PostMapping("/all")
    public void saveAll() {
        productService.saveAllProduct();
    }

}