package com.xmall.product.domain.service.impl;

import com.xmall.common.shared.exception.AppException;
import com.xmall.common.shared.exception.ErrorCode;
import com.xmall.product.application.dto.request.SPUImageRequest;
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
    public SPUImageResponse createSPUImage(SPUImageRequest spuImageRequest) {
        SPUEntity spu = spuRepository.findById(spuImageRequest.getSpuId())
                .orElseThrow(() -> {
                    ErrorCode errorCode = ErrorCode.NOT_EXISTED;
                    errorCode.setMessage("SPU not found.");
                    return new AppException(errorCode);
                });

        SPUImageEntity spuImageEntity = spuImageMapper.toSPUImageEntity(spuImageRequest);
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
    public SPUImageResponse updateSPUImage(Long id, SPUImageRequest spuImageRequest) {
        SPUImageEntity spuImageEntity = spuImageRepository.findById(id).orElseThrow(() -> {
            ErrorCode errorCode = ErrorCode.NOT_EXISTED;
            errorCode.setMessage("SPU image not found.");
            return new AppException(errorCode);
        });

        SPUEntity spu = spuRepository.findById(spuImageRequest.getSpuId())
                .orElseThrow(() -> {
                    ErrorCode errorCode = ErrorCode.NOT_EXISTED;
                    errorCode.setMessage("SPU not found.");
                    return new AppException(errorCode);
                });

        spuImageMapper.updateSPUImageEntity(spuImageEntity, spuImageRequest);
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