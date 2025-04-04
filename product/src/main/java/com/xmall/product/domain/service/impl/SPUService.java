package com.xmall.product.domain.service.impl;

import com.xmall.common.shared.exception.AppException;
import com.xmall.common.shared.exception.ErrorCode;
import com.xmall.product.application.dto.request.SPURequest;
import com.xmall.product.application.dto.response.SPUResponse;
import com.xmall.product.application.mapper.SPUMapper;
import com.xmall.product.domain.entity.CategoryEntity;
import com.xmall.product.domain.entity.SPUEntity;
import com.xmall.product.domain.service.IService;
import com.xmall.product.infrastructure.persistence.JpaCategoryRepository;
import com.xmall.product.infrastructure.persistence.JpaSPURepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SPUService implements IService {
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
    public SPUResponse createSPU(SPURequest spuRequest) {
        if (spuRepository.existsByName(spuRequest.getName())) {
            ErrorCode errorCode = ErrorCode.NOT_EXISTED;
            errorCode.setMessage("SPU with name '" + spuRequest.getName() + "' already exists.");
            throw new AppException(errorCode);
        }

        CategoryEntity category = categoryRepository.findById(spuRequest.getCategoryId())
                .orElseThrow(() -> {
                    ErrorCode errorCode = ErrorCode.NOT_EXISTED;
                    errorCode.setMessage("Category not found.");
                    return new AppException(errorCode);
                });

        SPUEntity spuEntity = spuMapper.toSPUEntity(spuRequest);
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
    public SPUResponse updateSPU(Long id, SPURequest spuRequest) {
        SPUEntity spuEntity = spuRepository.findById(id).orElseThrow(() -> {
            ErrorCode errorCode = ErrorCode.NOT_EXISTED;
            errorCode.setMessage("SPU not found.");
            return new AppException(errorCode);
        });

        if (!spuEntity.getName().equals(spuRequest.getName()) && spuRepository.existsByName(spuRequest.getName())) {
            ErrorCode errorCode = ErrorCode.NOT_EXISTED;
            errorCode.setMessage("SPU with name '" + spuRequest.getName() + "' already exists.");
            throw new AppException(errorCode);
        }

        CategoryEntity category = categoryRepository.findById(spuRequest.getCategoryId())
                .orElseThrow(() -> {
                    ErrorCode errorCode = ErrorCode.NOT_EXISTED;
                    errorCode.setMessage("Category not found.");
                    return new AppException(errorCode);
                });

        spuMapper.updateSPUEntity(spuEntity, spuRequest);
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