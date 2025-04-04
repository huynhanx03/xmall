package com.xmall.product.infrastructure.persistence;

import com.xmall.product.domain.entity.SPUImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaSPUImageRepository extends JpaRepository<SPUImageEntity, Long> {
    List<SPUImageEntity> findBySpuSpuId(Long spuId);
} 