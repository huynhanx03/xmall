package com.xmall.product.application.mapper;

import com.xmall.product.application.dto.request.CategoryRequest;
import com.xmall.product.application.dto.response.CategoryResponse;
import com.xmall.product.domain.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResponse toCategoryResponse(CategoryEntity categoryEntity);
    CategoryEntity toCategoryEntity(CategoryRequest categoryRequest);
    void updateCategoryEntity(@MappingTarget CategoryEntity categoryEntity, CategoryRequest categoryRequest);
} 