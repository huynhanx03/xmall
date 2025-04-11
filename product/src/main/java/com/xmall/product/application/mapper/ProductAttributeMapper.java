package com.xmall.product.application.mapper;

import com.xmall.product.application.dto.request.ProductAttributeCreateRequest;
import com.xmall.product.application.dto.request.ProductAttributeUpdateRequest;
import com.xmall.product.application.dto.response.ProductAttributeResponse;
import com.xmall.product.domain.entity.ProductAttributeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductAttributeMapper {
    ProductAttributeResponse toProductAttributeResponse(ProductAttributeEntity productAttributeEntity);
    ProductAttributeEntity toProductAttributeEntity(ProductAttributeCreateRequest productAttributeCreateRequest);
    void updateProductAttributeEntity(@MappingTarget ProductAttributeEntity productAttributeEntity, ProductAttributeUpdateRequest productAttributeUpdateRequest);
} 