package com.emotionalcart.hellosearchapi.presentation.product;

import com.emotionalcart.hellosearchapi.application.product.ProductService;
import io.swagger.v3.oas.annotations.Hidden;
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
    public void save(@RequestBody ProductSaveRequest request) throws IOException {
        productService.upsertProductToElastic(request);
    }

    @Hidden
    @PostMapping("/all")
    public void saveAll() {
        productService.saveAllProduct();
    }

}
// 캐럿 남녀공용 민소매 티셔츠 5컬러