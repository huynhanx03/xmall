package com.xmall.product.application.mapper;

import com.xmall.product.application.dto.request.BrandCreateRequest;
import com.xmall.product.application.dto.request.BrandUpdateRequest;
import com.xmall.product.application.dto.response.BrandResponse;
import com.xmall.product.domain.entity.BrandEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    BrandResponse toBrandResponse(BrandEntity brandEntity);
    BrandEntity toBrandEntity(BrandCreateRequest request);
    void updateBrandEntity(@MappingTarget BrandEntity brandEntity, BrandUpdateRequest request);
}
