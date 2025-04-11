package com.xmall.product.domain.service;

import com.xmall.product.application.dto.request.SKUCreateRequest;
import com.xmall.product.application.dto.request.SKUImageCreateRequest;
import com.xmall.product.application.dto.request.SKUImageUpdateRequest;
import com.xmall.product.application.dto.request.SKUUpdateRequest;
import com.xmall.product.application.dto.response.SKUImageResponse;

import java.util.List;

public interface ISKUImageService {
    List<SKUImageResponse> getSKUImages();
    SKUImageResponse getSKUImageById(Long id);
    SKUImageResponse createSKUImage(SKUImageCreateRequest request);
    SKUImageResponse updateSKUImage(Long id, SKUImageUpdateRequest request);
    void deleteSKUImage(Long id);
    List<SKUImageResponse> getSKUImagesBySKU(Long skuId);
} 