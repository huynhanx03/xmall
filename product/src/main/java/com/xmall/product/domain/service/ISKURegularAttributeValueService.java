package com.xmall.product.domain.service;

import com.xmall.product.application.dto.request.SKURegularAttributeValueRequest;
import com.xmall.product.application.dto.response.SKURegularAttributeValueResponse;

import java.util.List;

public interface ISKURegularAttributeValueService {
    List<SKURegularAttributeValueResponse> getSKURegularAttributeValues();
    SKURegularAttributeValueResponse getSKURegularAttributeValue(Long skuId, Long attributeId);
    SKURegularAttributeValueResponse createSKURegularAttributeValue(SKURegularAttributeValueRequest request);
    SKURegularAttributeValueResponse updateSKURegularAttributeValue(Long skuId, Long attributeId, SKURegularAttributeValueRequest request);
    void deleteSKURegularAttributeValue(Long skuId, Long attributeId);
    List<SKURegularAttributeValueResponse> getSKURegularAttributeValuesBySKU(Long skuId);
    List<SKURegularAttributeValueResponse> getSKURegularAttributeValuesByAttribute(Long attributeId);
} 