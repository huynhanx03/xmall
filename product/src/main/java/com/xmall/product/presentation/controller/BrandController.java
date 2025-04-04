package com.xmall.product.presentation.controller;

import com.xmall.common.application.dto.response.ApiResponse;
import com.xmall.product.application.dto.request.BrandRequest;
import com.xmall.product.application.dto.response.BrandResponse;
import com.xmall.product.domain.service.impl.BrandService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/brands")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BrandController {
    BrandService brandService;

    @GetMapping
    ApiResponse<List<BrandResponse>> getBrands() {
        return ApiResponse.<List<BrandResponse>>builder()
                .data(brandService.getBrands())
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<BrandResponse> getBrand(@PathVariable("id") Long id) {
        return ApiResponse.<BrandResponse>builder()
                .data(brandService.getBrandById(id))
                .build();
    }

    @PostMapping
    ApiResponse<BrandResponse> createBrand(@RequestBody @Valid BrandRequest request) {
        return ApiResponse.<BrandResponse>builder()
                .data(brandService.createBrand(request))
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<BrandResponse> updateBrand(@PathVariable Long id, @Valid @RequestBody BrandRequest request) {
        return ApiResponse.<BrandResponse>builder()
                .data(brandService.updateBrand(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteBrand(@PathVariable Long id) {
        brandService.deleteBrand(id);
        return ApiResponse.<Void>builder().build();
    }
}
