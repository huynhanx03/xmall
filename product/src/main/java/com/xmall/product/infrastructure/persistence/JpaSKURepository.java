package com.xmall.product.infrastructure.persistence;

import com.xmall.product.domain.entity.SKUEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaSKURepository extends JpaRepository<SKUEntity, Long> {
    boolean existsByName(String name);
    List<SKUEntity> findBySpuSpuId(Long spuId);
} 