package com.xmall.product.presentation.controller;

import com.xmall.product.application.dto.request.SPUImageRequest;
import com.xmall.product.application.dto.response.SPUImageResponse;
import com.xmall.product.domain.service.ISPUImageService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spu-images")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SPUImageController {
    ISPUImageService spuImageService;

    @GetMapping
    public ResponseEntity<List<SPUImageResponse>> getSPUImages() {
        return ResponseEntity.ok(spuImageService.getSPUImages());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SPUImageResponse> getSPUImageById(@PathVariable Long id) {
        return ResponseEntity.ok(spuImageService.getSPUImageById(id));
    }

    @PostMapping
    public ResponseEntity<SPUImageResponse> createSPUImage(@Valid @RequestBody SPUImageRequest spuImageRequest) {
        return new ResponseEntity<>(spuImageService.createSPUImage(spuImageRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SPUImageResponse> updateSPUImage(@PathVariable Long id, @Valid @RequestBody SPUImageRequest spuImageRequest) {
        return ResponseEntity.ok(spuImageService.updateSPUImage(id, spuImageRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSPUImage(@PathVariable Long id) {
        spuImageService.deleteSPUImage(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/spu/{spuId}")
    public ResponseEntity<List<SPUImageResponse>> getSPUImagesBySPU(@PathVariable Long spuId) {
        return ResponseEntity.ok(spuImageService.getSPUImagesBySPU(spuId));
    }
} 