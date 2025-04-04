package com.xmall.product.presentation.controller;

import com.xmall.product.application.dto.request.SKUImageRequest;
import com.xmall.product.application.dto.response.SKUImageResponse;
import com.xmall.product.domain.service.ISKUImageService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sku-images")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SKUImageController {
    ISKUImageService skuImageService;

    @GetMapping
    public ResponseEntity<List<SKUImageResponse>> getSKUImages() {
        return ResponseEntity.ok(skuImageService.getSKUImages());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SKUImageResponse> getSKUImageById(@PathVariable Long id) {
        return ResponseEntity.ok(skuImageService.getSKUImageById(id));
    }

    @PostMapping
    public ResponseEntity<SKUImageResponse> createSKUImage(@Valid @RequestBody SKUImageRequest skuImageRequest) {
        return new ResponseEntity<>(skuImageService.createSKUImage(skuImageRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SKUImageResponse> updateSKUImage(@PathVariable Long id, @Valid @RequestBody SKUImageRequest skuImageRequest) {
        return ResponseEntity.ok(skuImageService.updateSKUImage(id, skuImageRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSKUImage(@PathVariable Long id) {
        skuImageService.deleteSKUImage(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/sku/{skuId}")
    public ResponseEntity<List<SKUImageResponse>> getSKUImagesBySKU(@PathVariable Long skuId) {
        return ResponseEntity.ok(skuImageService.getSKUImagesBySKU(skuId));
    }
} 