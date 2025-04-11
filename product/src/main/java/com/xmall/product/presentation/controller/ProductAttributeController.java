package com.xmall.product.presentation.controller;

import com.xmall.common.application.dto.response.ApiResponse;
import com.xmall.product.application.dto.request.ProductAttributeCreateRequest;
import com.xmall.product.application.dto.request.ProductAttributeUpdateRequest;
import com.xmall.product.application.dto.response.ProductAttributeResponse;
import com.xmall.product.domain.service.IProductAttributeService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/attributes")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductAttributeController {
    IProductAttributeService productAttributeService;

    @GetMapping
    ApiResponse<List<ProductAttributeResponse>> getProductAttributes() {
        return ApiResponse.<List<ProductAttributeResponse>>builder()
                .data(productAttributeService.getProductAttributes())
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<ProductAttributeResponse> getProductAttribute(@PathVariable("id") Long id) {
        return ApiResponse.<ProductAttributeResponse>builder()
                .data(productAttributeService.getProductAttributeById(id))
                .build();
    }

    @PostMapping
    ApiResponse<ProductAttributeResponse> createProductAttribute(@RequestBody @Valid ProductAttributeCreateRequest request) {
        return ApiResponse.<ProductAttributeResponse>builder()
                .data(productAttributeService.createProductAttribute(request))
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<ProductAttributeResponse> updateProductAttribute(@PathVariable Long id, @Valid @RequestBody ProductAttributeUpdateRequest request) {
        return ApiResponse.<ProductAttributeResponse>builder()
                .data(productAttributeService.updateProductAttribute(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteProductAttribute(@PathVariable Long id) {
        productAttributeService.deleteProductAttribute(id);
        return ApiResponse.<Void>builder().build();
    }
} 