package com.xmall.product.domain.service;

import com.xmall.product.application.dto.request.SPUCreateRequest;
import com.xmall.product.application.dto.request.SPUUpdateRequest;
import com.xmall.product.application.dto.response.SPUResponse;

import java.util.List;

public interface ISPUService {
    List<SPUResponse> getSPUs();
    SPUResponse getSPUById(Long id);
    SPUResponse createSPU(SPUCreateRequest request);
    SPUResponse updateSPU(Long id, SPUUpdateRequest request);
    void deleteSPU(Long id);
    List<SPUResponse> getSPUsByCategory(Long categoryId);
    List<SPUResponse> getSPUsByBrand(Long brandId);
} 