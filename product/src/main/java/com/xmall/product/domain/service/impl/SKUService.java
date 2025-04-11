package com.xmall.product.domain.service.impl;

import com.xmall.common.shared.exception.AppException;
import com.xmall.common.shared.exception.ErrorCode;
import com.xmall.product.application.dto.request.SKUCreateRequest;
import com.xmall.product.application.dto.request.SKUUpdateRequest;
import com.xmall.product.application.dto.response.SKUResponse;
import com.xmall.product.application.mapper.SKUMapper;
import com.xmall.product.domain.entity.SKUEntity;
import com.xmall.product.domain.entity.SPUEntity;
import com.xmall.product.domain.service.ISKUService;
import com.xmall.product.infrastructure.persistence.JpaSKURepository;
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
public class SKUService implements ISKUService {
    JpaSKURepository skuRepository;
    JpaSPURepository spuRepository;
    SKUMapper skuMapper;

    @Override
    public List<SKUResponse> getSKUs() {
        return skuRepository.findAll().stream()
                .map(skuMapper::toSKUResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SKUResponse getSKUById(Long id) {
        return skuMapper.toSKUResponse(skuRepository.findById(id).orElseThrow(() -> {
            ErrorCode errorCode = ErrorCode.NOT_EXISTED;
            errorCode.setMessage("SKU not found.");
            return new AppException(errorCode);
        }));
    }

    @Override
    @Transactional
    public SKUResponse createSKU(SKUCreateRequest request) {
        if (skuRepository.existsByName(request.getName())) {
            ErrorCode errorCode = ErrorCode.NOT_EXISTED;
            errorCode.setMessage("SKU with name '" + request.getName() + "' already exists.");
            throw new AppException(errorCode);
        }

        SPUEntity spu = spuRepository.findById(request.getSpuId())
                .orElseThrow(() -> {
                    ErrorCode errorCode = ErrorCode.NOT_EXISTED;
                    errorCode.setMessage("SPU not found.");
                    return new AppException(errorCode);
                });

        SKUEntity skuEntity = skuMapper.toSKUEntity(request);
        skuEntity.setSpu(spu);

        try {
            skuEntity = skuRepository.save(skuEntity);
        } catch (Exception e) {
            ErrorCode errorCode = ErrorCode.FAILED;
            errorCode.setMessage(e.getMessage());
            throw new AppException(errorCode);
        }

        return skuMapper.toSKUResponse(skuEntity);
    }

    @Override
    @Transactional
    public SKUResponse updateSKU(Long id, SKUUpdateRequest request) {
        SKUEntity skuEntity = skuRepository.findById(id).orElseThrow(() -> {
            ErrorCode errorCode = ErrorCode.NOT_EXISTED;
            errorCode.setMessage("SKU not found.");
            return new AppException(errorCode);
        });

        if (!skuEntity.getName().equals(request.getName()) && skuRepository.existsByName(request.getName())) {
            ErrorCode errorCode = ErrorCode.NOT_EXISTED;
            errorCode.setMessage("SKU with name '" + request.getName() + "' already exists.");
            throw new AppException(errorCode);
        }

        SPUEntity spu = spuRepository.findById(request.getSpuId())
                .orElseThrow(() -> {
                    ErrorCode errorCode = ErrorCode.NOT_EXISTED;
                    errorCode.setMessage("SPU not found.");
                    return new AppException(errorCode);
                });

        skuMapper.updateSKUEntity(skuEntity, request);
        skuEntity.setSpu(spu);

        try {
            skuEntity = skuRepository.save(skuEntity);
        } catch (Exception e) {
            ErrorCode errorCode = ErrorCode.FAILED;
            errorCode.setMessage(e.getMessage());
            throw new AppException(errorCode);
        }

        return skuMapper.toSKUResponse(skuEntity);
    }

    @Override
    @Transactional
    public void deleteSKU(Long id) {
        if (!skuRepository.existsById(id)) {
            ErrorCode errorCode = ErrorCode.NOT_EXISTED;
            errorCode.setMessage("SKU not found.");
            throw new AppException(errorCode);
        }

        skuRepository.deleteById(id);
    }

    @Override
    public List<SKUResponse> getSKUsBySPU(Long spuId) {
        return skuRepository.findBySpuSpuId(spuId).stream()
                .map(skuMapper::toSKUResponse)
                .collect(Collectors.toList());
    }
} 