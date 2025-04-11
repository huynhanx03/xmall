package com.xmall.product.domain.service.impl;

import com.xmall.common.shared.exception.AppException;
import com.xmall.common.shared.exception.ErrorCode;
import com.xmall.product.application.dto.request.SKUSalesAttributeValueCreateRequest;
import com.xmall.product.application.dto.request.SKUSalesAttributeValueUpdateRequest;
import com.xmall.product.application.dto.response.SKUSalesAttributeValueResponse;
import com.xmall.product.application.mapper.SKUSalesAttributeValueMapper;
import com.xmall.product.domain.entity.ProductAttributeEntity;
import com.xmall.product.domain.entity.SKUEntity;
import com.xmall.product.domain.entity.SKUSalesAttributeValueEntity;
import com.xmall.product.domain.entity.key.SKUAttributeKey;
import com.xmall.product.domain.service.ISKUSalesAttributeValueService;
import com.xmall.product.infrastructure.persistence.JpaProductAttributeRepository;
import com.xmall.product.infrastructure.persistence.JpaSKUSalesAttributeValueRepository;
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
public class SKUSalesAttributeValueService implements ISKUSalesAttributeValueService {
    JpaSKUSalesAttributeValueRepository skuSalesAttributeValueRepository;
    JpaSKURepository skuRepository;
    JpaProductAttributeRepository productAttributeRepository;
    SKUSalesAttributeValueMapper skuSalesAttributeValueMapper;

    @Override
    public List<SKUSalesAttributeValueResponse> getSKUSalesAttributeValues() {
        return skuSalesAttributeValueRepository.findAll().stream()
                .map(skuSalesAttributeValueMapper::toSKUSalesAttributeValueResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SKUSalesAttributeValueResponse getSKUSalesAttributeValue(Long skuId, Long attributeId) {
        SKUAttributeKey id = new SKUAttributeKey(skuId, attributeId);
        return skuSalesAttributeValueMapper.toSKUSalesAttributeValueResponse(
                skuSalesAttributeValueRepository.findById(id).orElseThrow(() -> {
                    ErrorCode errorCode = ErrorCode.NOT_EXISTED;
                    errorCode.setMessage("SKU Sales Attribute Value not found.");
                    return new AppException(errorCode);
                })
        );
    }

    @Override
    @Transactional
    public SKUSalesAttributeValueResponse createSKUSalesAttributeValue(SKUSalesAttributeValueCreateRequest request) {
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

        if (attribute.getType() != 0) {
            ErrorCode errorCode = ErrorCode.INVALID;
            errorCode.setMessage("Attribute must be of type 0 (Sales).");
            throw new AppException(errorCode);
        }

        SKUAttributeKey id = new SKUAttributeKey(request.getSkuId(), request.getAttributeId());
        if (skuSalesAttributeValueRepository.existsById(id)) {
            ErrorCode errorCode = ErrorCode.NOT_EXISTED;
            errorCode.setMessage("SKU Sales Attribute Value already exists.");
            throw new AppException(errorCode);
        }

        SKUSalesAttributeValueEntity entity = skuSalesAttributeValueMapper.toSKUSalesAttributeValueEntity(request);
        entity.setSku(sku);
        entity.setAttribute(attribute);

        try {
            entity = skuSalesAttributeValueRepository.save(entity);
        } catch (Exception e) {
            ErrorCode errorCode = ErrorCode.FAILED;
            errorCode.setMessage(e.getMessage());
            throw new AppException(errorCode);
        }

        return skuSalesAttributeValueMapper.toSKUSalesAttributeValueResponse(entity);
    }

    @Override
    @Transactional
    public SKUSalesAttributeValueResponse updateSKUSalesAttributeValue(Long skuId, Long attributeId, SKUSalesAttributeValueUpdateRequest request) {
        SKUAttributeKey id = new SKUAttributeKey(skuId, attributeId);
        SKUSalesAttributeValueEntity entity = skuSalesAttributeValueRepository.findById(id)
                .orElseThrow(() -> {
                    ErrorCode errorCode = ErrorCode.NOT_EXISTED;
                    errorCode.setMessage("SKU Sales Attribute Value not found.");
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

        if (attribute.getType() != 0) {
            ErrorCode errorCode = ErrorCode.INVALID;
            errorCode.setMessage("Attribute must be of type 0 (Sales).");
            throw new AppException(errorCode);
        }

        skuSalesAttributeValueMapper.updateSKUSalesAttributeValueEntity(entity, request);
        entity.setSku(sku);
        entity.setAttribute(attribute);

        try {
            entity = skuSalesAttributeValueRepository.save(entity);
        } catch (Exception e) {
            ErrorCode errorCode = ErrorCode.FAILED;
            errorCode.setMessage(e.getMessage());
            throw new AppException(errorCode);
        }

        return skuSalesAttributeValueMapper.toSKUSalesAttributeValueResponse(entity);
    }

    @Override
    @Transactional
    public void deleteSKUSalesAttributeValue(Long skuId, Long attributeId) {
        SKUAttributeKey id = new SKUAttributeKey(skuId, attributeId);
        if (!skuSalesAttributeValueRepository.existsById(id)) {
            ErrorCode errorCode = ErrorCode.NOT_EXISTED;
            errorCode.setMessage("SKU Sales Attribute Value not found.");
            throw new AppException(errorCode);
        }

        skuSalesAttributeValueRepository.deleteById(id);
    }

    @Override
    public List<SKUSalesAttributeValueResponse> getSKUSalesAttributeValuesBySKU(Long skuId) {
        return skuSalesAttributeValueRepository.findBySkuSkuId(skuId).stream()
                .map(skuSalesAttributeValueMapper::toSKUSalesAttributeValueResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<SKUSalesAttributeValueResponse> getSKUSalesAttributeValuesByAttribute(Long attributeId) {
        return skuSalesAttributeValueRepository.findByAttributeAttributeId(attributeId).stream()
                .map(skuSalesAttributeValueMapper::toSKUSalesAttributeValueResponse)
                .collect(Collectors.toList());
    }
} 