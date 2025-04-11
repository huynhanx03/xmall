package com.xmall.product.domain.service.impl;

import com.xmall.common.shared.exception.AppException;
import com.xmall.common.shared.exception.ErrorCode;
import com.xmall.product.application.dto.request.ProductAttributeCreateRequest;
import com.xmall.product.application.dto.request.ProductAttributeUpdateRequest;
import com.xmall.product.application.dto.response.ProductAttributeResponse;
import com.xmall.product.application.mapper.ProductAttributeMapper;
import com.xmall.product.domain.entity.ProductAttributeEntity;
import com.xmall.product.domain.service.IProductAttributeService;
import com.xmall.product.infrastructure.persistence.JpaProductAttributeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductAttributeService implements IProductAttributeService {
    JpaProductAttributeRepository productAttributeRepository;
    ProductAttributeMapper productAttributeMapper;

    @Override
    public List<ProductAttributeResponse> getProductAttributes() {
        return productAttributeRepository.findAll().stream()
                .map(productAttributeMapper::toProductAttributeResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductAttributeResponse getProductAttributeById(Long id) {
        return productAttributeMapper.toProductAttributeResponse(productAttributeRepository.findById(id).orElseThrow(() -> {
            ErrorCode errorCode = ErrorCode.NOT_EXISTED;
            errorCode.setMessage("Product attribute not found.");
            return new AppException(errorCode);
        }));
    }

    @Override
    public ProductAttributeResponse createProductAttribute(ProductAttributeCreateRequest request) {
        if (productAttributeRepository.existsByName(request.getName())) {
            ErrorCode errorCode = ErrorCode.NOT_EXISTED;
            errorCode.setMessage("Product attribute with name '" + request.getName() + "' already exists.");
            throw new AppException(errorCode);
        }

        ProductAttributeEntity productAttributeEntity = productAttributeMapper.toProductAttributeEntity(request);

        try {
            productAttributeEntity = productAttributeRepository.save(productAttributeEntity);
        } catch (Exception e) {
            ErrorCode errorCode = ErrorCode.FAILED;
            errorCode.setMessage(e.getMessage());
            throw new AppException(errorCode);
        }

        return productAttributeMapper.toProductAttributeResponse(productAttributeEntity);
    }

    @Override
    public ProductAttributeResponse updateProductAttribute(Long id, ProductAttributeUpdateRequest request) {
        ProductAttributeEntity productAttributeEntity = productAttributeRepository.findById(id).orElseThrow(() -> {
            ErrorCode errorCode = ErrorCode.NOT_EXISTED;
            errorCode.setMessage("Product attribute not found.");
            return new AppException(errorCode);
        });

        if (productAttributeEntity.getName().equals(request.getName())) {
            return productAttributeMapper.toProductAttributeResponse(productAttributeRepository.save(productAttributeEntity));
        }

        if (productAttributeRepository.existsByName(request.getName())) {
            ErrorCode errorCode = ErrorCode.NOT_EXISTED;
            errorCode.setMessage("Product attribute with name '" + request.getName() + "' already exists.");
            throw new AppException(errorCode);
        }

        productAttributeMapper.updateProductAttributeEntity(productAttributeEntity, request);

        try {
            productAttributeEntity = productAttributeRepository.save(productAttributeEntity);
        } catch (Exception e) {
            ErrorCode errorCode = ErrorCode.FAILED;
            errorCode.setMessage(e.getMessage());
            throw new AppException(errorCode);
        }

        return productAttributeMapper.toProductAttributeResponse(productAttributeEntity);
    }

    @Override
    public void deleteProductAttribute(Long id) {
        if (!productAttributeRepository.existsById(id)) {
            ErrorCode errorCode = ErrorCode.NOT_EXISTED;
            errorCode.setMessage("Product attribute not found.");
            throw new AppException(errorCode);
        }

        productAttributeRepository.deleteById(id);
    }
} 