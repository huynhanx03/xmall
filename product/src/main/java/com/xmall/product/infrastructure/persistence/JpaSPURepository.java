package com.xmall.product.infrastructure.persistence;

import com.xmall.product.domain.entity.SPUEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaSPURepository extends JpaRepository<SPUEntity, Long> {
    boolean existsByName(String name);
    List<SPUEntity> findByCategoryCategoryId(Long categoryId);
    List<SPUEntity> findByBrandBrandId(Long brandId);
} 