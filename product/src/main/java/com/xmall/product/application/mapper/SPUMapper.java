package com.xmall.product.application.mapper;

import com.xmall.product.application.dto.request.SPUCreateRequest;
import com.xmall.product.application.dto.request.SPUUpdateRequest;
import com.xmall.product.application.dto.response.SPUResponse;
import com.xmall.product.domain.entity.SPUEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SPUMapper {
    @Mapping(target = "category.categoryId", source = "categoryId")
    @Mapping(target = "brand.brandId", source = "brandId")
    SPUEntity toSPUEntity(SPUCreateRequest spuCreateRequest);

    @Mapping(target = "categoryId", source = "category.categoryId")
    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "brandId", source = "brand.brandId")
    @Mapping(target = "brandName", source = "brand.name")
    SPUResponse toSPUResponse(SPUEntity spuEntity);

    @Mapping(target = "category.categoryId", source = "categoryId")
    @Mapping(target = "brand.brandId", source = "brandId")
    void updateSPUEntity(@MappingTarget SPUEntity spuEntity, SPUUpdateRequest spuUpdateRequest);
} 