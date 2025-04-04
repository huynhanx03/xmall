package com.xmall.product.domain.service;

import com.xmall.product.application.dto.request.SKURequest;
import com.xmall.product.application.dto.response.SKUResponse;

import java.util.List;

public interface ISKUService {
    List<SKUResponse> getSKUs();
    SKUResponse getSKUById(Long id);
    SKUResponse createSKU(SKURequest request);
    SKUResponse updateSKU(Long id, SKURequest request);
    void deleteSKU(Long id);
    List<SKUResponse> getSKUsBySPU(Long spuId);
} 