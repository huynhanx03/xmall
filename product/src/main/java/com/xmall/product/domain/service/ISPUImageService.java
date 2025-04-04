package com.xmall.product.domain.service;

import com.xmall.product.application.dto.request.SPUImageRequest;
import com.xmall.product.application.dto.response.SPUImageResponse;

import java.util.List;

public interface ISPUImageService {
    List<SPUImageResponse> getSPUImages();
    SPUImageResponse getSPUImageById(Long id);
    SPUImageResponse createSPUImage(SPUImageRequest request);
    SPUImageResponse updateSPUImage(Long id, SPUImageRequest request);
    void deleteSPUImage(Long id);
    List<SPUImageResponse> getSPUImagesBySPU(Long spuId);
} 