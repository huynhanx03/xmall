package com.xmall.product.domain.service;

import com.xmall.product.application.dto.request.CategoryCreateRequest;
import com.xmall.product.application.dto.request.CategoryUpdateRequest;
import com.xmall.product.application.dto.response.CategoryResponse;

import java.util.List;

public interface ICategoryService {
    List<CategoryResponse> getCategories();
    CategoryResponse getCategoryById(Long id);
    CategoryResponse createCategory(CategoryCreateRequest request);
    CategoryResponse updateCategory(Long id, CategoryUpdateRequest request);
    void deleteCategory(Long id);
} 