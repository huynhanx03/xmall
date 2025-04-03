package com.xmall.product.domain.service;

import com.xmall.product.application.dto.request.BrandRequest;
import com.xmall.product.application.dto.response.BrandResponse;

import java.util.List;

public interface IBrandService {
    List<BrandResponse> getBrands();
    BrandResponse getBrandById(Long id);
    BrandResponse createBrand(BrandRequest request);
    BrandResponse updateBrand(Long id, BrandRequest request);
    void deleteBrand(Long id);
}
