package com.xmall.product.domain.service.impl;

import com.xmall.common.shared.exception.AppException;
import com.xmall.common.shared.exception.ErrorCode;
import com.xmall.product.application.dto.request.CategoryCreateRequest;
import com.xmall.product.application.dto.request.CategoryUpdateRequest;
import com.xmall.product.application.dto.response.CategoryResponse;
import com.xmall.product.application.mapper.CategoryMapper;
import com.xmall.product.domain.entity.CategoryEntity;
import com.xmall.product.domain.service.ICategoryService;
import com.xmall.product.infrastructure.persistence.JpaCategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryService implements ICategoryService {
    JpaCategoryRepository categoryRepository;
    CategoryMapper categoryMapper;

    @Override
    public List<CategoryResponse> getCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toCategoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        return categoryMapper.toCategoryResponse(categoryRepository.findById(id).orElseThrow(() -> {
            ErrorCode errorCode = ErrorCode.NOT_EXISTED;
            errorCode.setMessage("Category not found.");
            return new AppException(errorCode);
        }));
    }

    @Override
    public CategoryResponse createCategory(CategoryCreateRequest request) {
        if (categoryRepository.existsByName(request.getName())) {
            ErrorCode errorCode = ErrorCode.NOT_EXISTED;
            errorCode.setMessage("Category with name '" + request.getName() + "' already exists.");
            throw new AppException(errorCode);
        }

        CategoryEntity categoryEntity = categoryMapper.toCategoryEntity(request);

        try {
            categoryEntity = categoryRepository.save(categoryEntity);
        } catch (Exception e) {
            ErrorCode errorCode = ErrorCode.FAILED;
            errorCode.setMessage(e.getMessage());
            throw new AppException(errorCode);
        }

        return categoryMapper.toCategoryResponse(categoryEntity);
    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryUpdateRequest categoryUpdateRequest) {
        CategoryEntity categoryEntity = categoryRepository.findById(id).orElseThrow(() -> {
            ErrorCode errorCode = ErrorCode.NOT_EXISTED;
            errorCode.setMessage("Category not found.");
            return new AppException(errorCode);
        });

        if (categoryEntity.getName().equals(categoryUpdateRequest.getName())) {
            return categoryMapper.toCategoryResponse(categoryRepository.save(categoryEntity));
        }

        if (categoryRepository.existsByName(categoryUpdateRequest.getName())) {
            ErrorCode errorCode = ErrorCode.NOT_EXISTED;
            errorCode.setMessage("Category with name '" + categoryUpdateRequest.getName() + "' already exists.");
            throw new AppException(errorCode);
        }

        categoryMapper.updateCategoryEntity(categoryEntity, categoryUpdateRequest);

        try {
            categoryEntity = categoryRepository.save(categoryEntity);
        } catch (Exception e) {
            ErrorCode errorCode = ErrorCode.FAILED;
            errorCode.setMessage(e.getMessage());
            throw new AppException(errorCode);
        }

        return categoryMapper.toCategoryResponse(categoryEntity);
    }

    @Override
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            ErrorCode errorCode = ErrorCode.NOT_EXISTED;
            errorCode.setMessage("Category not found.");
            throw new AppException(errorCode);
        }

        categoryRepository.deleteById(id);
    }
} 