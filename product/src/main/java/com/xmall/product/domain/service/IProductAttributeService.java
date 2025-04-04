package com.xmall.product.domain.service;

import com.xmall.product.application.dto.request.ProductAttributeRequest;
import com.xmall.product.application.dto.response.ProductAttributeResponse;

import java.util.List;

public interface IProductAttributeService {
    List<ProductAttributeResponse> getProductAttributes();
    ProductAttributeResponse getProductAttributeById(Long id);
    ProductAttributeResponse createProductAttribute(ProductAttributeRequest request);
    ProductAttributeResponse updateProductAttribute(Long id, ProductAttributeRequest request);
    void deleteProductAttribute(Long id);
} 