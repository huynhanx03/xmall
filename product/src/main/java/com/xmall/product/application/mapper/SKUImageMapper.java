package com.xmall.product.application.mapper;

import com.xmall.product.application.dto.request.SKUImageRequest;
import com.xmall.product.application.dto.response.SKUImageResponse;
import com.xmall.product.domain.entity.SKUImageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SKUImageMapper {
    @Mapping(target = "sku.skuId", source = "skuId")
    SKUImageEntity toSKUImageEntity(SKUImageRequest skuImageRequest);

    @Mapping(target = "skuId", source = "sku.skuId")
    @Mapping(target = "skuName", source = "sku.name")
    SKUImageResponse toSKUImageResponse(SKUImageEntity skuImageEntity);

    @Mapping(target = "sku.skuId", source = "skuId")
    void updateSKUImageEntity(@MappingTarget SKUImageEntity skuImageEntity, SKUImageRequest skuImageRequest);
} 