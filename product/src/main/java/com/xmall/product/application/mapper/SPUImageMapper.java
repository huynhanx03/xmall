package com.xmall.product.application.mapper;

import com.xmall.product.application.dto.request.SPUImageRequest;
import com.xmall.product.application.dto.response.SPUImageResponse;
import com.xmall.product.domain.entity.SPUImageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SPUImageMapper {
    @Mapping(target = "spu.spuId", source = "spuId")
    SPUImageEntity toSPUImageEntity(SPUImageRequest spuImageRequest);

    @Mapping(target = "spuId", source = "spu.spuId")
    @Mapping(target = "spuName", source = "spu.name")
    SPUImageResponse toSPUImageResponse(SPUImageEntity spuImageEntity);

    @Mapping(target = "spu.spuId", source = "spuId")
    void updateSPUImageEntity(@MappingTarget SPUImageEntity spuImageEntity, SPUImageRequest spuImageRequest);
} 