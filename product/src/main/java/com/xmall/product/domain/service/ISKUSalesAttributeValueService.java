package com.xmall.product.domain.service;

import com.xmall.product.application.dto.request.SKUSalesAttributeValueCreateRequest;
import com.xmall.product.application.dto.request.SKUSalesAttributeValueUpdateRequest;
import com.xmall.product.application.dto.response.SKUSalesAttributeValueResponse;

import java.util.List;

public interface ISKUSalesAttributeValueService {
    List<SKUSalesAttributeValueResponse> getSKUSalesAttributeValues();
    SKUSalesAttributeValueResponse getSKUSalesAttributeValue(Long skuId, Long attributeId);
    SKUSalesAttributeValueResponse createSKUSalesAttributeValue(SKUSalesAttributeValueCreateRequest request);
    SKUSalesAttributeValueResponse updateSKUSalesAttributeValue(Long skuId, Long attributeId, SKUSalesAttributeValueUpdateRequest request);
    void deleteSKUSalesAttributeValue(Long skuId, Long attributeId);
    List<SKUSalesAttributeValueResponse> getSKUSalesAttributeValuesBySKU(Long skuId);
    List<SKUSalesAttributeValueResponse> getSKUSalesAttributeValuesByAttribute(Long attributeId);
} 