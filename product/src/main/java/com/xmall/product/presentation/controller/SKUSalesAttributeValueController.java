package com.xmall.product.presentation.controller;

import com.xmall.common.application.dto.response.ApiResponse;
import com.xmall.product.application.dto.request.SKUSalesAttributeValueCreateRequest;
import com.xmall.product.application.dto.request.SKUSalesAttributeValueUpdateRequest;
import com.xmall.product.application.dto.response.SKUSalesAttributeValueResponse;
import com.xmall.product.domain.service.ISKUSalesAttributeValueService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sku-sales-attribute-values")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SKUSalesAttributeValueController {
    ISKUSalesAttributeValueService skuSalesAttributeValueService;

    @GetMapping
    ApiResponse<List<SKUSalesAttributeValueResponse>> getSKUSalesAttributeValues() {
        return ApiResponse.<List<SKUSalesAttributeValueResponse>>builder()
                .data(skuSalesAttributeValueService.getSKUSalesAttributeValues())
                .build();
    }

    @GetMapping("/{skuId}/{attributeId}")
    ApiResponse<SKUSalesAttributeValueResponse> getSKUSalesAttributeValue(
            @PathVariable Long skuId, @PathVariable Long attributeId) {
        return ApiResponse.<SKUSalesAttributeValueResponse>builder()
                .data(skuSalesAttributeValueService.getSKUSalesAttributeValue(skuId, attributeId))
                .build();
    }

    @PostMapping
    ApiResponse<SKUSalesAttributeValueResponse> createSKUSalesAttributeValue(
            @Valid @RequestBody SKUSalesAttributeValueCreateRequest request) {
        return ApiResponse.<SKUSalesAttributeValueResponse>builder()
                .data(skuSalesAttributeValueService.createSKUSalesAttributeValue(request))
                .build();
    }

    @PutMapping("/{skuId}/{attributeId}")
    ApiResponse<SKUSalesAttributeValueResponse> updateSKUSalesAttributeValue(
            @PathVariable Long skuId, @PathVariable Long attributeId,
            @Valid @RequestBody SKUSalesAttributeValueUpdateRequest request) {
        return ApiResponse.<SKUSalesAttributeValueResponse>builder()
                .data(skuSalesAttributeValueService.updateSKUSalesAttributeValue(skuId, attributeId, request))
                .build();
    }

    @DeleteMapping("/{skuId}/{attributeId}")
    ApiResponse<Void> deleteSKUSalesAttributeValue(
            @PathVariable Long skuId, @PathVariable Long attributeId) {
        skuSalesAttributeValueService.deleteSKUSalesAttributeValue(skuId, attributeId);
        return ApiResponse.<Void>builder().build();
    }

    @GetMapping("/sku/{skuId}")
    ApiResponse<List<SKUSalesAttributeValueResponse>> getSKUSalesAttributeValuesBySKU(
            @PathVariable Long skuId) {
        return ApiResponse.<List<SKUSalesAttributeValueResponse>>builder()
                .data(skuSalesAttributeValueService.getSKUSalesAttributeValuesBySKU(skuId))
                .build();
    }

    @GetMapping("/attribute/{attributeId}")
    ApiResponse<List<SKUSalesAttributeValueResponse>> getSKUSalesAttributeValuesByAttribute(
            @PathVariable Long attributeId) {
        return ApiResponse.<List<SKUSalesAttributeValueResponse>>builder()
                .data(skuSalesAttributeValueService.getSKUSalesAttributeValuesByAttribute(attributeId))
                .build();
    }
} 