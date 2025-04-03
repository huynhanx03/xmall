package com.xmall.product.application.mapper;

import com.xmall.product.application.dto.request.BrandRequest;
import com.xmall.product.application.dto.response.BrandResponse;
import com.xmall.product.domain.entity.BrandEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    BrandResponse toBrandResponse(BrandEntity brandEntity);
    BrandEntity toBrandEntity(BrandRequest brandRequest);
    void updateBrandEntity(@MappingTarget BrandEntity brandEntity, BrandRequest brandRequest);
}
