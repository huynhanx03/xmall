package com.xmall.product.application.mapper;

import com.xmall.product.application.dto.request.SKUCreateRequest;
import com.xmall.product.application.dto.request.SKUUpdateRequest;
import com.xmall.product.application.dto.response.SKUResponse;
import com.xmall.product.domain.entity.SKUEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SKUMapper {
    @Mapping(target = "spu.spuId", source = "spuId")
    SKUEntity toSKUEntity(SKUCreateRequest request);

    @Mapping(target = "spuId", source = "spu.spuId")
    @Mapping(target = "spuName", source = "spu.name")
    SKUResponse toSKUResponse(SKUEntity skuEntity);

    @Mapping(target = "spu.spuId", source = "spuId")
    void updateSKUEntity(@MappingTarget SKUEntity skuEntity, SKUUpdateRequest request);
} 