package com.xmall.product.domain.service;

import com.xmall.product.application.dto.request.ProductAttributeCreateRequest;
import com.xmall.product.application.dto.request.ProductAttributeUpdateRequest;
import com.xmall.product.application.dto.response.ProductAttributeResponse;

import java.util.List;

public interface IProductAttributeService {
    List<ProductAttributeResponse> getProductAttributes();
    ProductAttributeResponse getProductAttributeById(Long id);
    ProductAttributeResponse createProductAttribute(ProductAttributeCreateRequest request);
    ProductAttributeResponse updateProductAttribute(Long id, ProductAttributeUpdateRequest request);
    void deleteProductAttribute(Long id);
} 