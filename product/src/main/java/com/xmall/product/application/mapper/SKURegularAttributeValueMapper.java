package com.xmall.product.application.mapper;

import com.xmall.product.application.dto.request.SKURegularAttributeValueCreateRequest;
import com.xmall.product.application.dto.request.SKURegularAttributeValueUpdateRequest;
import com.xmall.product.application.dto.response.SKURegularAttributeValueResponse;
import com.xmall.product.domain.entity.SKURegularAttributeValueEntity;
import com.xmall.product.domain.entity.key.SKUAttributeKey;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SKURegularAttributeValueMapper {
    @Mapping(target = "id.skuId", source = "skuId")
    @Mapping(target = "id.attributeId", source = "attributeId")
    SKURegularAttributeValueEntity toSKURegularAttributeValueEntity(SKURegularAttributeValueCreateRequest request);

    @Mapping(target = "skuId", source = "sku.skuId")
    @Mapping(target = "skuName", source = "sku.name")
    @Mapping(target = "attributeId", source = "attribute.attributeId")
    @Mapping(target = "attributeName", source = "attribute.name")
    SKURegularAttributeValueResponse toSKURegularAttributeValueResponse(SKURegularAttributeValueEntity entity);

    @Mapping(target = "id.skuId", source = "skuId")
    @Mapping(target = "id.attributeId", source = "attributeId")
    void updateSKURegularAttributeValueEntity(@MappingTarget SKURegularAttributeValueEntity entity, SKURegularAttributeValueUpdateRequest request);
} 