package com.emotionalcart.hellosearchapi.presentation.product;

import com.emotionalcart.hellosearchapi.application.product.ProductDeleteService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductDeleteController {

    private final ProductDeleteService productDeleteService;

    @Operation(summary = "상품 삭제", description = "상품을 삭제합니다.")
    @DeleteMapping("/{productId}")
    public void deleteProduct(@PathVariable("productId") Long productId) throws IOException {
        productDeleteService.deleteProduct(productId);
    }

}
