package com.xmall.product.domain.service.impl;

import com.xmall.common.shared.exception.AppException;
import com.xmall.common.shared.exception.ErrorCode;
import com.xmall.product.application.dto.request.SKUImageCreateRequest;
import com.xmall.product.application.dto.request.SKUImageUpdateRequest;
import com.xmall.product.application.dto.response.SKUImageResponse;
import com.xmall.product.application.mapper.SKUImageMapper;
import com.xmall.product.domain.entity.SKUEntity;
import com.xmall.product.domain.entity.SKUImageEntity;
import com.xmall.product.domain.service.ISKUImageService;
import com.xmall.product.infrastructure.persistence.JpaSKUImageRepository;
import com.xmall.product.infrastructure.persistence.JpaSKURepository;
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
public class SKUImageService implements ISKUImageService {

    private final JpaSKUImageRepository skuImageRepository;
    private final JpaSKURepository skuRepository;
    private final SKUImageMapper skuImageMapper;

    @Override
    public List<SKUImageResponse> getSKUImages() {
        return skuImageRepository.findAll().stream()
                .map(skuImageMapper::toSKUImageResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SKUImageResponse getSKUImageById(Long id) {
        return skuImageMapper.toSKUImageResponse(skuImageRepository.findById(id).orElseThrow(() -> {
            ErrorCode errorCode = ErrorCode.NOT_EXISTED;
            errorCode.setMessage("SKU image not found.");
            return new AppException(errorCode);
        }));
    }

    @Override
    public SKUImageResponse createSKUImage(SKUImageCreateRequest request) {
        SKUEntity sku = skuRepository.findById(request.getSkuId())
                .orElseThrow(() -> {
                    ErrorCode errorCode = ErrorCode.NOT_EXISTED;
                    errorCode.setMessage("SKU not found.");
                    return new AppException(errorCode);
                });

        SKUImageEntity skuImageEntity = skuImageMapper.toSKUImageEntity(request);
        skuImageEntity.setSku(sku);

        try {
            skuImageEntity = skuImageRepository.save(skuImageEntity);
        } catch (Exception e) {
            ErrorCode errorCode = ErrorCode.FAILED;
            errorCode.setMessage(e.getMessage());
            throw new AppException(errorCode);
        }

        return skuImageMapper.toSKUImageResponse(skuImageEntity);
    }

    @Override
    @Transactional
    public SKUImageResponse updateSKUImage(Long id, SKUImageUpdateRequest request) {
        SKUImageEntity skuImageEntity = skuImageRepository.findById(id).orElseThrow(() -> {
            ErrorCode errorCode = ErrorCode.NOT_EXISTED;
            errorCode.setMessage("SKU image not found.");
            return new AppException(errorCode);
        });

        SKUEntity sku = skuRepository.findById(request.getSkuId())
                .orElseThrow(() -> {
                    ErrorCode errorCode = ErrorCode.NOT_EXISTED;
                    errorCode.setMessage("SKU not found.");
                    return new AppException(errorCode);
                });

        skuImageEntity.setImageUrl(request.getImageUrl());
        skuImageEntity.setSku(sku);

        try {
            skuImageEntity = skuImageRepository.save(skuImageEntity);
        } catch (Exception e) {
            ErrorCode errorCode = ErrorCode.FAILED;
            errorCode.setMessage(e.getMessage());
            throw new AppException(errorCode);
        }

        return skuImageMapper.toSKUImageResponse(skuImageEntity);
    }

    @Override
    public void deleteSKUImage(Long id) {
        if (!skuImageRepository.existsById(id)) {
            ErrorCode errorCode = ErrorCode.NOT_EXISTED;
            errorCode.setMessage("SKU image not found.");
            throw new AppException(errorCode);
        }

        skuImageRepository.deleteById(id);
    }

    @Override
    public List<SKUImageResponse> getSKUImagesBySKU(Long skuId) {
        return skuImageRepository.findBySkuSkuId(skuId).stream()
                .map(skuImageMapper::toSKUImageResponse)
                .collect(Collectors.toList());
    }
} 