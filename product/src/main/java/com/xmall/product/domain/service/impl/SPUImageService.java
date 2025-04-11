package com.xmall.product.domain.service.impl;

import com.xmall.common.shared.exception.AppException;
import com.xmall.common.shared.exception.ErrorCode;
import com.xmall.product.application.dto.request.SPUImageCreateRequest;
import com.xmall.product.application.dto.request.SPUImageUpdateRequest;
import com.xmall.product.application.dto.response.SPUImageResponse;
import com.xmall.product.application.mapper.SPUImageMapper;
import com.xmall.product.domain.entity.SPUEntity;
import com.xmall.product.domain.entity.SPUImageEntity;
import com.xmall.product.domain.service.ISPUImageService;
import com.xmall.product.infrastructure.persistence.JpaSPUImageRepository;
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
public class SPUImageService implements ISPUImageService {
    JpaSPUImageRepository spuImageRepository;
    JpaSPURepository spuRepository;
    SPUImageMapper spuImageMapper;

    @Override
    public List<SPUImageResponse> getSPUImages() {
        return spuImageRepository.findAll().stream()
                .map(spuImageMapper::toSPUImageResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SPUImageResponse getSPUImageById(Long id) {
        return spuImageMapper.toSPUImageResponse(spuImageRepository.findById(id).orElseThrow(() -> {
            ErrorCode errorCode = ErrorCode.NOT_EXISTED;
            errorCode.setMessage("SPU image not found.");
            return new AppException(errorCode);
        }));
    }

    @Override
    @Transactional
    public SPUImageResponse createSPUImage(SPUImageCreateRequest request) {
        SPUEntity spu = spuRepository.findById(request.getSpuId())
                .orElseThrow(() -> {
                    ErrorCode errorCode = ErrorCode.NOT_EXISTED;
                    errorCode.setMessage("SPU not found.");
                    return new AppException(errorCode);
                });

        SPUImageEntity spuImageEntity = spuImageMapper.toSPUImageEntity(request);
        spuImageEntity.setSpu(spu);

        try {
            spuImageEntity = spuImageRepository.save(spuImageEntity);
        } catch (Exception e) {
            ErrorCode errorCode = ErrorCode.FAILED;
            errorCode.setMessage(e.getMessage());
            throw new AppException(errorCode);
        }

        return spuImageMapper.toSPUImageResponse(spuImageEntity);
    }

    @Override
    @Transactional
    public SPUImageResponse updateSPUImage(Long id, SPUImageUpdateRequest request) {
        SPUImageEntity spuImageEntity = spuImageRepository.findById(id).orElseThrow(() -> {
            ErrorCode errorCode = ErrorCode.NOT_EXISTED;
            errorCode.setMessage("SPU image not found.");
            return new AppException(errorCode);
        });

        if (spuImageEntity.getImageUrl().equals(request.getImageUrl())) {
            return spuImageMapper.toSPUImageResponse(spuImageRepository.save(spuImageEntity));
        }

        SPUEntity spu = spuRepository.findById(request.getSpuId())
                .orElseThrow(() -> {
                    ErrorCode errorCode = ErrorCode.NOT_EXISTED;
                    errorCode.setMessage("SPU not found.");
                    return new AppException(errorCode);
                });

        spuImageEntity.setImageUrl(request.getImageUrl());
        spuImageEntity.setSpu(spu);

        try {
            spuImageEntity = spuImageRepository.save(spuImageEntity);
        } catch (Exception e) {
            ErrorCode errorCode = ErrorCode.FAILED;
            errorCode.setMessage(e.getMessage());
            throw new AppException(errorCode);
        }

        return spuImageMapper.toSPUImageResponse(spuImageEntity);
    }

    @Override
    public void deleteSPUImage(Long id) {
        if (!spuImageRepository.existsById(id)) {
            ErrorCode errorCode = ErrorCode.NOT_EXISTED;
            errorCode.setMessage("SPU image not found.");
            throw new AppException(errorCode);
        }

        spuImageRepository.deleteById(id);
    }

    @Override
    public List<SPUImageResponse> getSPUImagesBySPU(Long spuId) {
        return spuImageRepository.findBySpuSpuId(spuId).stream()
                .map(spuImageMapper::toSPUImageResponse)
                .collect(Collectors.toList());
    }
} 