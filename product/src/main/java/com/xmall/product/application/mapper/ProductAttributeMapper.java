package com.xmall.product.application.mapper;

import com.xmall.product.application.dto.request.ProductAttributeRequest;
import com.xmall.product.application.dto.response.ProductAttributeResponse;
import com.xmall.product.domain.entity.ProductAttributeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductAttributeMapper {
    ProductAttributeResponse toProductAttributeResponse(ProductAttributeEntity productAttributeEntity);
    ProductAttributeEntity toProductAttributeEntity(ProductAttributeRequest productAttributeRequest);
    void updateProductAttributeEntity(@MappingTarget ProductAttributeEntity productAttributeEntity, ProductAttributeRequest productAttributeRequest);
} 