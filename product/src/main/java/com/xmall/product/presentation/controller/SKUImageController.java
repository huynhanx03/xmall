package com.xmall.product.presentation.controller;

import com.xmall.common.application.dto.response.ApiResponse;
import com.xmall.product.application.dto.request.SKUImageCreateRequest;
import com.xmall.product.application.dto.request.SKUImageUpdateRequest;
import com.xmall.product.application.dto.response.SKUImageResponse;
import com.xmall.product.domain.service.ISKUImageService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sku-images")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SKUImageController {
    ISKUImageService skuImageService;

    @GetMapping
    ApiResponse<List<SKUImageResponse>> getSKUImages() {
        return ApiResponse.<List<SKUImageResponse>>builder()
                .data(skuImageService.getSKUImages())
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<SKUImageResponse> getSKUImage(@PathVariable("id") Long id) {
        return ApiResponse.<SKUImageResponse>builder()
                .data(skuImageService.getSKUImageById(id))
                .build();
    }

    @PostMapping
    ApiResponse<SKUImageResponse> createSKUImage(@RequestBody @Valid SKUImageCreateRequest request) {
        return ApiResponse.<SKUImageResponse>builder()
                .data(skuImageService.createSKUImage(request))
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<SKUImageResponse> updateSKUImage(@PathVariable Long id, @Valid @RequestBody SKUImageUpdateRequest request) {
        return ApiResponse.<SKUImageResponse>builder()
                .data(skuImageService.updateSKUImage(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteSKUImage(@PathVariable Long id) {
        skuImageService.deleteSKUImage(id);
        return ApiResponse.<Void>builder().build();
    }

    @GetMapping("/sku/{skuId}")
    ApiResponse<List<SKUImageResponse>> getSKUImagesBySKU(@PathVariable Long skuId) {
        return ApiResponse.<List<SKUImageResponse>>builder()
                .data(skuImageService.getSKUImagesBySKU(skuId))
                .build();
    }
} 