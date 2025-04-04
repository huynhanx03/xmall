package com.xmall.product.presentation.controller;

import com.xmall.common.application.dto.response.ApiResponse;
import com.xmall.product.application.dto.request.SPURequest;
import com.xmall.product.application.dto.response.SPUResponse;
import com.xmall.product.domain.service.impl.SPUService;
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
    SPUService spuService;

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

    @PostMapping
    ApiResponse<SPUResponse> createSPU(@RequestBody @Valid SPURequest request) {
        return ApiResponse.<SPUResponse>builder()
                .data(spuService.createSPU(request))
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<SPUResponse> updateSPU(@PathVariable Long id, @Valid @RequestBody SPURequest request) {
        return ApiResponse.<SPUResponse>builder()
                .data(spuService.updateSPU(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteSPU(@PathVariable Long id) {
        spuService.deleteSPU(id);
        return ApiResponse.<Void>builder().build();
    }
} 