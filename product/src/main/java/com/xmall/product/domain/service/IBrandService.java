package com.xmall.product.domain.service;

import com.xmall.product.application.dto.request.BrandCreateRequest;
import com.xmall.product.application.dto.request.BrandUpdateRequest;
import com.xmall.product.application.dto.response.BrandResponse;

import java.util.List;

public interface IBrandService {
    List<BrandResponse> getBrands();
    BrandResponse getBrandById(Long id);
    BrandResponse createBrand(BrandCreateRequest request);
    BrandResponse updateBrand(Long id, BrandUpdateRequest request);
    void deleteBrand(Long id);
}
