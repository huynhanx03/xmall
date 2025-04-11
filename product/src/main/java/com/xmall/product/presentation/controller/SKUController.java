package com.xmall.product.presentation.controller;

import com.xmall.common.application.dto.response.ApiResponse;
import com.xmall.product.application.dto.request.SKUCreateRequest;
import com.xmall.product.application.dto.request.SKUUpdateRequest;
import com.xmall.product.application.dto.response.SKUResponse;
import com.xmall.product.domain.service.ISKUService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skus")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SKUController {
    ISKUService skuService;

    @GetMapping
    ApiResponse<List<SKUResponse>> getSKUs() {
        return ApiResponse.<List<SKUResponse>>builder()
                .data(skuService.getSKUs())
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<SKUResponse> getSKUById(@PathVariable Long id) {
        return ApiResponse.<SKUResponse>builder()
                .data(skuService.getSKUById(id))
                .build();
    }

    @PostMapping
    ApiResponse<SKUResponse> createSKU(@Valid @RequestBody SKUCreateRequest request) {
        return ApiResponse.<SKUResponse>builder()
                .data(skuService.createSKU(request))
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<SKUResponse> updateSKU(
            @PathVariable Long id, @Valid @RequestBody SKUUpdateRequest request) {
        return ApiResponse.<SKUResponse>builder()
                .data(skuService.updateSKU(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteSKU(@PathVariable Long id) {
        skuService.deleteSKU(id);
        return ApiResponse.<Void>builder().build();
    }

    @GetMapping("/spu/{spuId}")
    ApiResponse<List<SKUResponse>> getSKUsBySPU(@PathVariable Long spuId) {
        return ApiResponse.<List<SKUResponse>>builder()
                .data(skuService.getSKUsBySPU(spuId))
                .build();
    }
} 