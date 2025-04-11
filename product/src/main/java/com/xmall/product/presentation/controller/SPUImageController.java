package com.xmall.product.presentation.controller;

import com.xmall.common.application.dto.response.ApiResponse;
import com.xmall.product.application.dto.request.SPUImageCreateRequest;
import com.xmall.product.application.dto.request.SPUImageUpdateRequest;
import com.xmall.product.application.dto.response.SPUImageResponse;
import com.xmall.product.domain.service.ISPUImageService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spu-images")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SPUImageController {
    ISPUImageService spuImageService;

    @GetMapping
    ApiResponse<List<SPUImageResponse>> getSPUImages() {
        return ApiResponse.<List<SPUImageResponse>>builder()
                .data(spuImageService.getSPUImages())
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<SPUImageResponse> getSPUImage(@PathVariable("id") Long id) {
        return ApiResponse.<SPUImageResponse>builder()
                .data(spuImageService.getSPUImageById(id))
                .build();
    }

    @PostMapping
    ApiResponse<SPUImageResponse> createSPUImage(@RequestBody @Valid SPUImageCreateRequest request) {
        return ApiResponse.<SPUImageResponse>builder()
                .data(spuImageService.createSPUImage(request))
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<SPUImageResponse> updateSPUImage(@PathVariable Long id, @Valid @RequestBody SPUImageUpdateRequest request) {
        return ApiResponse.<SPUImageResponse>builder()
                .data(spuImageService.updateSPUImage(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteSPUImage(@PathVariable Long id) {
        spuImageService.deleteSPUImage(id);
        return ApiResponse.<Void>builder().build();
    }

    @GetMapping("/spu/{spuId}")
    ApiResponse<List<SPUImageResponse>> getSPUImagesBySPU(@PathVariable Long spuId) {
        return ApiResponse.<List<SPUImageResponse>>builder()
                .data(spuImageService.getSPUImagesBySPU(spuId))
                .build();
    }
} 