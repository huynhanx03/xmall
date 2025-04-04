package com.xmall.product.presentation.controller;

import com.xmall.product.application.dto.request.SKURegularAttributeValueRequest;
import com.xmall.product.application.dto.response.SKURegularAttributeValueResponse;
import com.xmall.product.domain.service.ISKURegularAttributeValueService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sku-regular-attribute-values")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SKURegularAttributeValueController {
    ISKURegularAttributeValueService skuRegularAttributeValueService;

    @GetMapping
    public ResponseEntity<List<SKURegularAttributeValueResponse>> getSKURegularAttributeValues() {
        return ResponseEntity.ok(skuRegularAttributeValueService.getSKURegularAttributeValues());
    }

    @GetMapping("/{skuId}/{attributeId}")
    public ResponseEntity<SKURegularAttributeValueResponse> getSKURegularAttributeValue(
            @PathVariable Long skuId, @PathVariable Long attributeId) {
        return ResponseEntity.ok(skuRegularAttributeValueService.getSKURegularAttributeValue(skuId, attributeId));
    }

    @PostMapping
    public ResponseEntity<SKURegularAttributeValueResponse> createSKURegularAttributeValue(
            @Valid @RequestBody SKURegularAttributeValueRequest request) {
        return new ResponseEntity<>(skuRegularAttributeValueService.createSKURegularAttributeValue(request), HttpStatus.CREATED);
    }

    @PutMapping("/{skuId}/{attributeId}")
    public ResponseEntity<SKURegularAttributeValueResponse> updateSKURegularAttributeValue(
            @PathVariable Long skuId, @PathVariable Long attributeId, @Valid @RequestBody SKURegularAttributeValueRequest request) {
        return ResponseEntity.ok(skuRegularAttributeValueService.updateSKURegularAttributeValue(skuId, attributeId, request));
    }

    @DeleteMapping("/{skuId}/{attributeId}")
    public ResponseEntity<Void> deleteSKURegularAttributeValue(
            @PathVariable Long skuId, @PathVariable Long attributeId) {
        skuRegularAttributeValueService.deleteSKURegularAttributeValue(skuId, attributeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/sku/{skuId}")
    public ResponseEntity<List<SKURegularAttributeValueResponse>> getSKURegularAttributeValuesBySKU(
            @PathVariable Long skuId) {
        return ResponseEntity.ok(skuRegularAttributeValueService.getSKURegularAttributeValuesBySKU(skuId));
    }

    @GetMapping("/attribute/{attributeId}")
    public ResponseEntity<List<SKURegularAttributeValueResponse>> getSKURegularAttributeValuesByAttribute(
            @PathVariable Long attributeId) {
        return ResponseEntity.ok(skuRegularAttributeValueService.getSKURegularAttributeValuesByAttribute(attributeId));
    }
} 