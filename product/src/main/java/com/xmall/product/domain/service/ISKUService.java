package com.xmall.product.domain.service;

import com.xmall.product.application.dto.request.SKUCreateRequest;
import com.xmall.product.application.dto.request.SKUUpdateRequest;
import com.xmall.product.application.dto.response.SKUResponse;

import java.util.List;

public interface ISKUService {
    List<SKUResponse> getSKUs();
    SKUResponse getSKUById(Long id);
    SKUResponse createSKU(SKUCreateRequest request);
    SKUResponse updateSKU(Long id, SKUUpdateRequest request);
    void deleteSKU(Long id);
    List<SKUResponse> getSKUsBySPU(Long spuId);
} 