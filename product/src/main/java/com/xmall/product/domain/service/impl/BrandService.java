package com.xmall.product.domain.service.impl;

import com.xmall.common.shared.exception.AppException;
import com.xmall.common.shared.exception.ErrorCode;
import com.xmall.product.application.dto.request.BrandCreateRequest;
import com.xmall.product.application.dto.request.BrandUpdateRequest;
import com.xmall.product.application.dto.response.BrandResponse;
import com.xmall.product.application.mapper.BrandMapper;
import com.xmall.product.domain.entity.BrandEntity;
import com.xmall.product.domain.service.IBrandService;
import com.xmall.product.infrastructure.persistence.JpaBrandRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BrandService implements IBrandService {
    JpaBrandRepository brandRepository;
    BrandMapper brandMapper;

    @Override
    public List<BrandResponse> getBrands() {
        return brandRepository.findAll().stream()
                .map(brandMapper::toBrandResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BrandResponse getBrandById(Long id) {
        return brandMapper.toBrandResponse(brandRepository.findById(id).orElseThrow(() -> {
                ErrorCode errorCode = ErrorCode.NOT_EXISTED;
                errorCode.setMessage("Brand not found.");
                return new AppException(errorCode);
            }
        ));
    }

    @Override
    public BrandResponse createBrand(BrandCreateRequest request) {
        if (brandRepository.existsByName(request.getName())) {
            ErrorCode errorCode = ErrorCode.NOT_EXISTED;
            errorCode.setMessage("Brand with name '" + request.getName() + "' already exists.");
            throw new AppException(errorCode);
        }

        BrandEntity brandEntity = brandMapper.toBrandEntity(request);

        try {
            brandEntity = brandRepository.save(brandEntity);
        } catch (Exception e) {
            ErrorCode errorCode = ErrorCode.FAILED;
            errorCode.setMessage(e.getMessage());
            throw new AppException(errorCode);
        }

        return brandMapper.toBrandResponse(brandEntity);
    }

    @Override
    public BrandResponse updateBrand(Long id, BrandUpdateRequest request) {
        BrandEntity brandEntity = brandRepository.findById(id).orElseThrow(() -> {
            ErrorCode errorCode = ErrorCode.NOT_EXISTED;
            errorCode.setMessage("Brand not found.");
            return new AppException(errorCode);
        });

        if (brandEntity.getName().equals(request.getName())) {
            return brandMapper.toBrandResponse(brandEntity);
        }

        if (brandRepository.existsByName(request.getName())) {
            ErrorCode errorCode = ErrorCode.NOT_EXISTED;
            errorCode.setMessage("Brand with name '" + request.getName() + "' already exists.");
            throw new AppException(errorCode);
        }

        brandMapper.updateBrandEntity(brandEntity, request);

        try {
            brandEntity = brandRepository.save(brandEntity);
        } catch (Exception e) {
            ErrorCode errorCode = ErrorCode.FAILED;
            errorCode.setMessage(e.getMessage());
            throw new AppException(errorCode);
        }

        return brandMapper.toBrandResponse(brandEntity);
    }

    @Override
    public void deleteBrand(Long id) {
        if (!brandRepository.existsById(id)) {
            ErrorCode errorCode = ErrorCode.NOT_EXISTED;
            errorCode.setMessage("Brand not found.");
            throw new AppException(errorCode);
        }

        brandRepository.deleteById(id);
    }
}
