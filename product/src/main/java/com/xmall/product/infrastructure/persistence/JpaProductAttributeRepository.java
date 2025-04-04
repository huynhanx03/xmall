package com.xmall.product.infrastructure.persistence;

import com.xmall.product.domain.entity.ProductAttributeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaProductAttributeRepository extends JpaRepository<ProductAttributeEntity, Long> {
    boolean existsByName(String name);
    boolean existsByNameAndType(String name, Integer type);
} 