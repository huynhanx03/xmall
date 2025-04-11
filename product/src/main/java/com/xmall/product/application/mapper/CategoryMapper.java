package com.xmall.product.application.mapper;

import com.xmall.product.application.dto.request.CategoryCreateRequest;
import com.xmall.product.application.dto.request.CategoryUpdateRequest;
import com.xmall.product.application.dto.response.CategoryResponse;
import com.xmall.product.domain.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResponse toCategoryResponse(CategoryEntity categoryEntity);
    CategoryEntity toCategoryEntity(CategoryCreateRequest request);
    void updateCategoryEntity(@MappingTarget CategoryEntity categoryEntity, CategoryUpdateRequest request);
} 