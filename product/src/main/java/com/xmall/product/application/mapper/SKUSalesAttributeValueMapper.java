package com.xmall.product.application.mapper;

import com.xmall.product.application.dto.request.SKUSalesAttributeValueCreateRequest;
import com.xmall.product.application.dto.request.SKUSalesAttributeValueUpdateRequest;
import com.xmall.product.application.dto.response.SKUSalesAttributeValueResponse;
import com.xmall.product.domain.entity.SKUSalesAttributeValueEntity;
import com.xmall.product.domain.entity.key.SKUAttributeKey;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SKUSalesAttributeValueMapper {
    @Mapping(target = "id.skuId", source = "skuId")
    @Mapping(target = "id.attributeId", source = "attributeId")
    SKUSalesAttributeValueEntity toSKUSalesAttributeValueEntity(SKUSalesAttributeValueCreateRequest request);

    @Mapping(target = "skuId", source = "sku.skuId")
    @Mapping(target = "skuName", source = "sku.name")
    @Mapping(target = "attributeId", source = "attribute.attributeId")
    @Mapping(target = "attributeName", source = "attribute.name")
    SKUSalesAttributeValueResponse toSKUSalesAttributeValueResponse(SKUSalesAttributeValueEntity entity);

    @Mapping(target = "id.skuId", source = "skuId")
    @Mapping(target = "id.attributeId", source = "attributeId")
    void updateSKUSalesAttributeValueEntity(@MappingTarget SKUSalesAttributeValueEntity entity, SKUSalesAttributeValueUpdateRequest request);
} 