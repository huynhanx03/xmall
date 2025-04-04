package com.xmall.product.domain.service;

import com.xmall.product.application.dto.request.SKUImageRequest;
import com.xmall.product.application.dto.response.SKUImageResponse;

import java.util.List;

public interface ISKUImageService {
    List<SKUImageResponse> getSKUImages();
    SKUImageResponse getSKUImageById(Long id);
    SKUImageResponse createSKUImage(SKUImageRequest request);
    SKUImageResponse updateSKUImage(Long id, SKUImageRequest request);
    void deleteSKUImage(Long id);
    List<SKUImageResponse> getSKUImagesBySKU(Long skuId);
} 