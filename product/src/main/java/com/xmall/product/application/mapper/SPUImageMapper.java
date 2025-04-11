package com.xmall.product.application.mapper;

import com.xmall.product.application.dto.request.SPUImageCreateRequest;
import com.xmall.product.application.dto.request.SPUImageUpdateRequest;
import com.xmall.product.application.dto.response.SPUImageResponse;
import com.xmall.product.domain.entity.SPUImageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SPUImageMapper {
    @Mapping(target = "spu.spuId", source = "spuId")
    SPUImageEntity toSPUImageEntity(SPUImageCreateRequest spuImageCreateRequest);

    @Mapping(target = "spuId", source = "spu.spuId")
    @Mapping(target = "spuName", source = "spu.name")
    SPUImageResponse toSPUImageResponse(SPUImageEntity spuImageEntity);

    @Mapping(target = "spu.spuId", source = "spuId")
    void updateSPUImageEntity(@MappingTarget SPUImageEntity spuImageEntity, SPUImageUpdateRequest spuImageUpdateRequest);
} 