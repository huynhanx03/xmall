package com.xmall.product.infrastructure.persistence;

import com.xmall.product.domain.entity.SKUImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaSKUImageRepository extends JpaRepository<SKUImageEntity, Long> {
    List<SKUImageEntity> findBySkuSkuId(Long skuId);
} 