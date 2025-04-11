package com.xmall.product.infrastructure.persistence;

import com.xmall.product.domain.entity.SKURegularAttributeValueEntity;
import com.xmall.product.domain.entity.key.SKUAttributeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaSKURegularAttributeValueRepository extends JpaRepository<SKURegularAttributeValueEntity, SKUAttributeKey> {
    List<SKURegularAttributeValueEntity> findBySkuSkuId(Long skuId);
    List<SKURegularAttributeValueEntity> findByAttributeAttributeId(Long attributeId);

    boolean existsByIdSkuIdAndIdAttributeId(Long idSkuId, Long idAttributeId);
}