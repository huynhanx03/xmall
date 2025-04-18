package com.xmall.product.domain.service.impl;

import com.xmall.common.shared.exception.AppException;
import com.xmall.common.shared.exception.ErrorCode;
import com.xmall.product.application.dto.request.SKURegularAttributeValueCreateRequest;
import com.xmall.product.application.dto.request.SKURegularAttributeValueUpdateRequest;
import com.xmall.product.application.dto.response.SKURegularAttributeValueResponse;
import com.xmall.product.application.mapper.SKURegularAttributeValueMapper;
import com.xmall.product.domain.entity.ProductAttributeEntity;
import com.xmall.product.domain.entity.SKUEntity;
import com.xmall.product.domain.entity.SKURegularAttributeValueEntity;
import com.xmall.product.domain.entity.key.SKUAttributeKey;
import com.xmall.product.domain.service.ISKURegularAttributeValueService;
import com.xmall.product.infrastructure.persistence.JpaProductAttributeRepository;
import com.xmall.product.infrastructure.persistence.JpaSKURegularAttributeValueRepository;
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
public class SKURegularAttributeValueService implements ISKURegularAttributeValueService {
    JpaSKURegularAttributeValueRepository skuRegularAttributeValueRepository;
    JpaSKURepository skuRepository;
    JpaProductAttributeRepository productAttributeRepository;
    SKURegularAttributeValueMapper skuRegularAttributeValueMapper;

    @Override
    public List<SKURegularAttributeValueResponse> getSKURegularAttributeValues() {
        return skuRegularAttributeValueRepository.findAll().stream()
                .map(skuRegularAttributeValueMapper::toSKURegularAttributeValueResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SKURegularAttributeValueResponse getSKURegularAttributeValue(Long skuId, Long attributeId) {
        SKUAttributeKey id = new SKUAttributeKey(skuId, attributeId);
        return skuRegularAttributeValueMapper.toSKURegularAttributeValueResponse(
                skuRegularAttributeValueRepository.findById(id).orElseThrow(() -> {
                    ErrorCode errorCode = ErrorCode.NOT_EXISTED;
                    errorCode.setMessage("SKU Regular Attribute Value not found.");
                    return new AppException(errorCode);
                })
        );
    }

    @Override
    @Transactional
    public SKURegularAttributeValueResponse createSKURegularAttributeValue(SKURegularAttributeValueCreateRequest request) {
        SKUEntity sku = skuRepository.findById(request.getSkuId())
                .orElseThrow(() -> {
                    ErrorCode errorCode = ErrorCode.NOT_EXISTED;
                    errorCode.setMessage("SKU not found.");
                    return new AppException(errorCode);
                });

        ProductAttributeEntity attribute = productAttributeRepository.findById(request.getAttributeId())
                .orElseThrow(() -> {
                    ErrorCode errorCode = ErrorCode.NOT_EXISTED;
                    errorCode.setMessage("Product Attribute not found.");
                    return new AppException(errorCode);
                });

        if (attribute.getType() != 1) {
            ErrorCode errorCode = ErrorCode.INVALID;
            errorCode.setMessage("Attribute must be of type 1 (Regular).");
            throw new AppException(errorCode);
        }

        SKUAttributeKey id = new SKUAttributeKey(request.getSkuId(), request.getAttributeId());
        if (skuRegularAttributeValueRepository.existsById(id)) {
            ErrorCode errorCode = ErrorCode.NOT_EXISTED;
            errorCode.setMessage("SKU Regular Attribute Value already exists.");
            throw new AppException(errorCode);
        }

        SKURegularAttributeValueEntity entity = skuRegularAttributeValueMapper.toSKURegularAttributeValueEntity(request);
        entity.setSku(sku);
        entity.setAttribute(attribute);

        try {
            entity = skuRegularAttributeValueRepository.save(entity);
        } catch (Exception e) {
            ErrorCode errorCode = ErrorCode.FAILED;
            errorCode.setMessage(e.getMessage());
            throw new AppException(errorCode);
        }

        return skuRegularAttributeValueMapper.toSKURegularAttributeValueResponse(entity);
    }

    @Override
    @Transactional
    public SKURegularAttributeValueResponse updateSKURegularAttributeValue(Long skuId, Long attributeId, SKURegularAttributeValueUpdateRequest request) {
        SKUAttributeKey id = new SKUAttributeKey(skuId, attributeId);
        SKURegularAttributeValueEntity entity = skuRegularAttributeValueRepository.findById(id)
                .orElseThrow(() -> {
                    ErrorCode errorCode = ErrorCode.NOT_EXISTED;
                    errorCode.setMessage("SKU Regular Attribute Value not found.");
                    return new AppException(errorCode);
                });

        SKUEntity sku = skuRepository.findById(request.getSkuId())
                .orElseThrow(() -> {
                    ErrorCode errorCode = ErrorCode.NOT_EXISTED;
                    errorCode.setMessage("SKU not found.");
                    return new AppException(errorCode);
                });

        ProductAttributeEntity attribute = productAttributeRepository.findById(request.getAttributeId())
                .orElseThrow(() -> {
                    ErrorCode errorCode = ErrorCode.NOT_EXISTED;
                    errorCode.setMessage("Product Attribute not found.");
                    return new AppException(errorCode);
                });

        if (attribute.getType() != 1) {
            ErrorCode errorCode = ErrorCode.INVALID;
            errorCode.setMessage("Attribute must be of type 1 (Regular).");
            throw new AppException(errorCode);
        }

        skuRegularAttributeValueMapper.updateSKURegularAttributeValueEntity(entity, request);
        entity.setSku(sku);
        entity.setAttribute(attribute);

        try {
            entity = skuRegularAttributeValueRepository.save(entity);
        } catch (Exception e) {
            ErrorCode errorCode = ErrorCode.FAILED;
            errorCode.setMessage(e.getMessage());
            throw new AppException(errorCode);
        }

        return skuRegularAttributeValueMapper.toSKURegularAttributeValueResponse(entity);
    }

    @Override
    @Transactional
    public void deleteSKURegularAttributeValue(Long skuId, Long attributeId) {
        SKUAttributeKey id = new SKUAttributeKey(skuId, attributeId);
        if (!skuRegularAttributeValueRepository.existsById(id)) {
            ErrorCode errorCode = ErrorCode.NOT_EXISTED;
            errorCode.setMessage("SKU Regular Attribute Value not found.");
            throw new AppException(errorCode);
        }

        skuRegularAttributeValueRepository.deleteById(id);
    }

    @Override
    public List<SKURegularAttributeValueResponse> getSKURegularAttributeValuesBySKU(Long skuId) {
        return skuRegularAttributeValueRepository.findBySkuSkuId(skuId).stream()
                .map(skuRegularAttributeValueMapper::toSKURegularAttributeValueResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<SKURegularAttributeValueResponse> getSKURegularAttributeValuesByAttribute(Long attributeId) {
        return skuRegularAttributeValueRepository.findByAttributeAttributeId(attributeId).stream()
                .map(skuRegularAttributeValueMapper::toSKURegularAttributeValueResponse)
                .collect(Collectors.toList());
    }
} 