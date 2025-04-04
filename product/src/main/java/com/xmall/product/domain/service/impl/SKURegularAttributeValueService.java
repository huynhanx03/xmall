package com.xmall.product.domain.service.impl;

import com.xmall.common.shared.exception.AppException;
import com.xmall.common.shared.exception.ErrorCode;
import com.xmall.product.application.dto.request.SKURegularAttributeValueRequest;
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
        SKUAttributeKey id = new SKUAttributeKey();
        id.setSkuId(skuId);
        id.setAttributeId(attributeId);
        
        return skuRegularAttributeValueMapper.toSKURegularAttributeValueResponse(
            skuRegularAttributeValueRepository.findById(id).orElseThrow(() -> {
                ErrorCode errorCode = ErrorCode.NOT_EXISTED;
                errorCode.setMessage("SKU regular attribute value not found.");
                return new AppException(errorCode);
            })
        );
    }

    @Override
    public SKURegularAttributeValueResponse createSKURegularAttributeValue(SKURegularAttributeValueRequest request) {
        // Check if SKU exists
        SKUEntity sku = skuRepository.findById(request.getSkuId())
                .orElseThrow(() -> {
                    ErrorCode errorCode = ErrorCode.NOT_EXISTED;
                    errorCode.setMessage("SKU not found.");
                    return new AppException(errorCode);
                });

        // Check if attribute exists and is a regular attribute (type = 1)
        ProductAttributeEntity attribute = productAttributeRepository.findById(request.getAttributeId())
                .orElseThrow(() -> {
                    ErrorCode errorCode = ErrorCode.NOT_EXISTED;
                    errorCode.setMessage("Product attribute not found.");
                    return new AppException(errorCode);
                });

        if (attribute.getType() != 1) {
            ErrorCode errorCode = ErrorCode.NOT_EXISTED;
            errorCode.setMessage("Attribute must be a regular attribute (type = 1).");
            throw new AppException(errorCode);
        }

        // Check if the combination already exists
        if (skuRegularAttributeValueRepository.existsBySkuSkuIdAndAttributeAttributeId(request.getSkuId(), request.getAttributeId())) {
            ErrorCode errorCode = ErrorCode.NOT_EXISTED;
            errorCode.setMessage("SKU regular attribute value already exists.");
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
    public SKURegularAttributeValueResponse updateSKURegularAttributeValue(Long skuId, Long attributeId, SKURegularAttributeValueRequest request) {
        SKUAttributeKey id = new SKUAttributeKey();
        id.setSkuId(skuId);
        id.setAttributeId(attributeId);
        
        SKURegularAttributeValueEntity entity = skuRegularAttributeValueRepository.findById(id).orElseThrow(() -> {
            ErrorCode errorCode = ErrorCode.NOT_EXISTED;
            errorCode.setMessage("SKU regular attribute value not found.");
            return new AppException(errorCode);
        });

        // Check if SKU exists
        SKUEntity sku = skuRepository.findById(request.getSkuId())
                .orElseThrow(() -> {
                    ErrorCode errorCode = ErrorCode.NOT_EXISTED;
                    errorCode.setMessage("SKU not found.");
                    return new AppException(errorCode);
                });

        // Check if attribute exists and is a regular attribute (type = 1)
        ProductAttributeEntity attribute = productAttributeRepository.findById(request.getAttributeId())
                .orElseThrow(() -> {
                    ErrorCode errorCode = ErrorCode.NOT_EXISTED;
                    errorCode.setMessage("Product attribute not found.");
                    return new AppException(errorCode);
                });

        if (attribute.getType() != 1) {
            ErrorCode errorCode = ErrorCode.NOT_EXISTED;
            errorCode.setMessage("Attribute must be a regular attribute (type = 1).");
            throw new AppException(errorCode);
        }

        // Check if the new combination already exists (if SKU or attribute is changed)
        if ((!skuId.equals(request.getSkuId()) || !attributeId.equals(request.getAttributeId())) && 
            skuRegularAttributeValueRepository.existsBySkuSkuIdAndAttributeAttributeId(request.getSkuId(), request.getAttributeId())) {
            ErrorCode errorCode = ErrorCode.NOT_EXISTED;
            errorCode.setMessage("SKU regular attribute value already exists.");
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
    public void deleteSKURegularAttributeValue(Long skuId, Long attributeId) {
        SKUAttributeKey id = new SKUAttributeKey();
        id.setSkuId(skuId);
        id.setAttributeId(attributeId);
        
        if (!skuRegularAttributeValueRepository.existsById(id)) {
            ErrorCode errorCode = ErrorCode.NOT_EXISTED;
            errorCode.setMessage("SKU regular attribute value not found.");
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