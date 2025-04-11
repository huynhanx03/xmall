package com.xmall.product.infrastructure.persistence;

import com.xmall.product.domain.entity.SKUSalesAttributeValueEntity;
import com.xmall.product.domain.entity.key.SKUAttributeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaSKUSalesAttributeValueRepository extends JpaRepository<SKUSalesAttributeValueEntity, SKUAttributeKey> {
    List<SKUSalesAttributeValueEntity> findBySkuSkuId(Long skuId);
    List<SKUSalesAttributeValueEntity> findByAttributeAttributeId(Long attributeId);
    boolean existsBySkuSkuIdAndAttributeAttributeId(Long skuId, Long attributeId);
} 