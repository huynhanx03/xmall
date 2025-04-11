package com.xmall.product.presentation.controller;

import com.xmall.common.application.dto.response.ApiResponse;
import com.xmall.product.application.dto.request.SKURegularAttributeValueCreateRequest;
import com.xmall.product.application.dto.request.SKURegularAttributeValueUpdateRequest;
import com.xmall.product.application.dto.response.SKURegularAttributeValueResponse;
import com.xmall.product.domain.service.ISKURegularAttributeValueService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sku-regular-attribute-values")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SKURegularAttributeValueController {
    ISKURegularAttributeValueService skuRegularAttributeValueService;

    @GetMapping
    ApiResponse<List<SKURegularAttributeValueResponse>> getSKURegularAttributeValues() {
        return ApiResponse.<List<SKURegularAttributeValueResponse>>builder()
                .data(skuRegularAttributeValueService.getSKURegularAttributeValues())
                .build();
    }

    @GetMapping("/{skuId}/{attributeId}")
    ApiResponse<SKURegularAttributeValueResponse> getSKURegularAttributeValue(
            @PathVariable Long skuId, @PathVariable Long attributeId) {
        return ApiResponse.<SKURegularAttributeValueResponse>builder()
                .data(skuRegularAttributeValueService.getSKURegularAttributeValue(skuId, attributeId))
                .build();
    }

    @PostMapping
    ApiResponse<SKURegularAttributeValueResponse> createSKURegularAttributeValue(
            @Valid @RequestBody SKURegularAttributeValueCreateRequest request) {
        return ApiResponse.<SKURegularAttributeValueResponse>builder()
                .data(skuRegularAttributeValueService.createSKURegularAttributeValue(request))
                .build();
    }

    @PutMapping("/{skuId}/{attributeId}")
    ApiResponse<SKURegularAttributeValueResponse> updateSKURegularAttributeValue(
            @PathVariable Long skuId, @PathVariable Long attributeId,
            @Valid @RequestBody SKURegularAttributeValueUpdateRequest request) {
        return ApiResponse.<SKURegularAttributeValueResponse>builder()
                .data(skuRegularAttributeValueService.updateSKURegularAttributeValue(skuId, attributeId, request))
                .build();
    }

    @DeleteMapping("/{skuId}/{attributeId}")
    ApiResponse<Void> deleteSKURegularAttributeValue(
            @PathVariable Long skuId, @PathVariable Long attributeId) {
        skuRegularAttributeValueService.deleteSKURegularAttributeValue(skuId, attributeId);
        return ApiResponse.<Void>builder().build();
    }

    @GetMapping("/sku/{skuId}")
    ApiResponse<List<SKURegularAttributeValueResponse>> getSKURegularAttributeValuesBySKU(
            @PathVariable Long skuId) {
        return ApiResponse.<List<SKURegularAttributeValueResponse>>builder()
                .data(skuRegularAttributeValueService.getSKURegularAttributeValuesBySKU(skuId))
                .build();
    }

    @GetMapping("/attribute/{attributeId}")
    ApiResponse<List<SKURegularAttributeValueResponse>> getSKURegularAttributeValuesByAttribute(
            @PathVariable Long attributeId) {
        return ApiResponse.<List<SKURegularAttributeValueResponse>>builder()
                .data(skuRegularAttributeValueService.getSKURegularAttributeValuesByAttribute(attributeId))
                .build();
    }
} 