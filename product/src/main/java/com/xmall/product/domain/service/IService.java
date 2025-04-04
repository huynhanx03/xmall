package com.xmall.product.domain.service;

import com.xmall.product.application.dto.request.SPURequest;
import com.xmall.product.application.dto.response.SPUResponse;

import java.util.List;

public interface IService {
    List<SPUResponse> getSPUs();
    SPUResponse getSPUById(Long id);
    SPUResponse createSPU(SPURequest request);
    SPUResponse updateSPU(Long id, SPURequest request);
    void deleteSPU(Long id);
    List<SPUResponse> getSPUsByCategory(Long categoryId);
    List<SPUResponse> getSPUsByBrand(Long brandId);
} 