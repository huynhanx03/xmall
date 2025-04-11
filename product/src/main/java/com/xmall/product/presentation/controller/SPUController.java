package com.xmall.product.presentation.controller;

import com.xmall.common.application.dto.response.ApiResponse;
import com.xmall.product.application.dto.request.SPUCreateRequest;
import com.xmall.product.application.dto.request.SPUUpdateRequest;
import com.xmall.product.application.dto.response.SPUResponse;
import com.xmall.product.domain.service.ISPUService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/spus")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SPUController {
    ISPUService spuService;

    @GetMapping
    ApiResponse<List<SPUResponse>> getSPUs() {
        return ApiResponse.<List<SPUResponse>>builder()
                .data(spuService.getSPUs())
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<SPUResponse> getSPU(@PathVariable("id") Long id) {
        return ApiResponse.<SPUResponse>builder()
                .data(spuService.getSPUById(id))
                .build();
    }

    @PostMapping
    ApiResponse<SPUResponse> createSPU(@RequestBody @Valid SPUCreateRequest request) {
        return ApiResponse.<SPUResponse>builder()
                .data(spuService.createSPU(request))
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<SPUResponse> updateSPU(@PathVariable Long id, @Valid @RequestBody SPUUpdateRequest request) {
        return ApiResponse.<SPUResponse>builder()
                .data(spuService.updateSPU(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteSPU(@PathVariable Long id) {
        spuService.deleteSPU(id);
        return ApiResponse.<Void>builder().build();
    }

    @GetMapping("/category/{categoryId}")
    ApiResponse<List<SPUResponse>> getSPUsByCategory(@PathVariable("categoryId") Long categoryId) {
        return ApiResponse.<List<SPUResponse>>builder()
                .data(spuService.getSPUsByCategory(categoryId))
                .build();
    }

    @GetMapping("/brand/{brandId}")
    ApiResponse<List<SPUResponse>> getSPUsByBrand(@PathVariable("brandId") Long brandId) {
        return ApiResponse.<List<SPUResponse>>builder()
                .data(spuService.getSPUsByBrand(brandId))
                .build();
    }
} 