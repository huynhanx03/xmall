package com.xmall.product.domain.service.impl;

import com.xmall.common.shared.exception.AppException;
import com.xmall.common.shared.exception.ErrorCode;
import com.xmall.product.application.dto.request.SPUCreateRequest;
import com.xmall.product.application.dto.request.SPUUpdateRequest;
import com.xmall.product.application.dto.response.SPUResponse;
import com.xmall.product.application.mapper.SPUMapper;
import com.xmall.product.domain.entity.CategoryEntity;
import com.xmall.product.domain.entity.SPUEntity;
import com.xmall.product.domain.service.ISPUService;
import com.xmall.product.infrastructure.persistence.JpaCategoryRepository;
import com.xmall.product.infrastructure.persistence.JpaSPURepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SPUService implements ISPUService {
    JpaSPURepository spuRepository;
    JpaCategoryRepository categoryRepository;
    SPUMapper spuMapper;

    @Override
    public List<SPUResponse> getSPUs() {
        return spuRepository.findAll().stream()
                .map(spuMapper::toSPUResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SPUResponse getSPUById(Long id) {
        return spuMapper.toSPUResponse(spuRepository.findById(id).orElseThrow(() -> {
            ErrorCode errorCode = ErrorCode.NOT_EXISTED;
            errorCode.setMessage("SPU not found.");
            return new AppException(errorCode);
        }));
    }

    @Override
    @Transactional
    public SPUResponse createSPU(SPUCreateRequest request) {
        if (spuRepository.existsByName(request.getName())) {
            ErrorCode errorCode = ErrorCode.NOT_EXISTED;
            errorCode.setMessage("SPU with name '" + request.getName() + "' already exists.");
            throw new AppException(errorCode);
        }

        CategoryEntity category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> {
                    ErrorCode errorCode = ErrorCode.NOT_EXISTED;
                    errorCode.setMessage("Category not found.");
                    return new AppException(errorCode);
                });

        SPUEntity spuEntity = spuMapper.toSPUEntity(request);
        spuEntity.setCategory(category);

        try {
            spuEntity = spuRepository.save(spuEntity);
        } catch (Exception e) {
            ErrorCode errorCode = ErrorCode.FAILED;
            errorCode.setMessage(e.getMessage());
            throw new AppException(errorCode);
        }

        return spuMapper.toSPUResponse(spuEntity);
    }

    @Override
    @Transactional
    public SPUResponse updateSPU(Long id, SPUUpdateRequest request) {
        SPUEntity spuEntity = spuRepository.findById(id).orElseThrow(() -> {
            ErrorCode errorCode = ErrorCode.NOT_EXISTED;
            errorCode.setMessage("SPU not found.");
            return new AppException(errorCode);
        });

        if (!spuEntity.getName().equals(request.getName()) && spuRepository.existsByName(request.getName())) {
            ErrorCode errorCode = ErrorCode.NOT_EXISTED;
            errorCode.setMessage("SPU with name '" + request.getName() + "' already exists.");
            throw new AppException(errorCode);
        }

        CategoryEntity category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> {
                    ErrorCode errorCode = ErrorCode.NOT_EXISTED;
                    errorCode.setMessage("Category not found.");
                    return new AppException(errorCode);
                });

        spuMapper.updateSPUEntity(spuEntity, request);
        spuEntity.setCategory(category);

        try {
            spuEntity = spuRepository.save(spuEntity);
        } catch (Exception e) {
            ErrorCode errorCode = ErrorCode.FAILED;
            errorCode.setMessage(e.getMessage());
            throw new AppException(errorCode);
        }

        return spuMapper.toSPUResponse(spuEntity);
    }

    @Override
    public void deleteSPU(Long id) {
        if (!spuRepository.existsById(id)) {
            ErrorCode errorCode = ErrorCode.NOT_EXISTED;
            errorCode.setMessage("SPU not found.");
            throw new AppException(errorCode);
        }

        spuRepository.deleteById(id);
    }

    @Override
    public List<SPUResponse> getSPUsByCategory(Long categoryId) {
        return spuRepository.findByCategoryCategoryId(categoryId).stream()
                .map(spuMapper::toSPUResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<SPUResponse> getSPUsByBrand(Long brandId) {
        return spuRepository.findByBrandBrandId(brandId).stream()
                .map(spuMapper::toSPUResponse)
                .collect(Collectors.toList());
    }
} 