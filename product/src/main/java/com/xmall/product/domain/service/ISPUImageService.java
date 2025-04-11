package com.xmall.product.domain.service;

import com.xmall.product.application.dto.request.SPUImageCreateRequest;
import com.xmall.product.application.dto.request.SPUImageUpdateRequest;
import com.xmall.product.application.dto.response.SPUImageResponse;

import java.util.List;

public interface ISPUImageService {
    List<SPUImageResponse> getSPUImages();
    SPUImageResponse getSPUImageById(Long id);
    SPUImageResponse createSPUImage(SPUImageCreateRequest request);
    SPUImageResponse updateSPUImage(Long id, SPUImageUpdateRequest request);
    void deleteSPUImage(Long id);
    List<SPUImageResponse> getSPUImagesBySPU(Long spuId);
} 